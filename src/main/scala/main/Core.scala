import chisel3._
import chisel3.util._
import _root_.circt.stage.ChiselStage

class Core extends Module {
  val io = IO(new Bundle {
    val debug_memory_write = Input(Bool());
    val debug_memory_write_address = Input(UInt(8.W));
    val debug_memory_write_data = Input(UInt(8.W));

    val debug_dispatcher_thread_requesting_opcode = Input(Bool());
    val debug_dispatcher_thread_program_pointer = Input(UInt(8.W));

    val debug_dispatcher_opcode = Output(Operation());
    val debug_dispatcher_program_pointer = Output(UInt(8.W));
  });

  val memory = Module(new Memory());
  val dispatcher = Module(new Dispatcher());

  memory.io.readPorts(0).enable := dispatcher.io.read_requested;
  memory.io.readPorts(0).address := dispatcher.io.read_program_pointer;

  memory.io.writePorts(0).enable := io.debug_memory_write;
  memory.io.writePorts(0).address := io.debug_memory_write_address;
  memory.io.writePorts(0).data := io.debug_memory_write_data;

  dispatcher.io.thread_requesting_opcode := io.debug_dispatcher_thread_requesting_opcode;
  dispatcher.io.thread_program_pointer := io.debug_dispatcher_thread_program_pointer;
  
  val read_ready_delayed = RegNext(dispatcher.io.read_requested, false.B);
  dispatcher.io.read_ready := read_ready_delayed;   
  dispatcher.io.read_opcode := Operation.safe(memory.io.readPorts(0).data(3, 0))._1;

  // when(true.B) {
  //   printf(p"\t[Core]=====");
  //   printf(p"\n\tdebug.thread_requesting_opcode=${io.debug_dispatcher_thread_requesting_opcode}");
  //   printf(p"\n\tdispatcher.read_requested=${dispatcher.io.read_requested}");
  //   printf(p"\n\tread_ready_delayed=${read_ready_delayed}\n\n");
  // }

  io.debug_dispatcher_opcode := dispatcher.io.opcode;
  io.debug_dispatcher_program_pointer := dispatcher.io.program_pointer;
}
