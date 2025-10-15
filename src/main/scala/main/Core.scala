import chisel3._
import chisel3.util._
import _root_.circt.stage.ChiselStage

class Core extends Module {
  val io = IO(new Bundle {
    val debug_memory_write = Input(Bool());
    val debug_memory_write_address = Input(UInt(8.W));
    val debug_memory_write_data = Input(UInt(8.W));

    val debug_dispatcher_opcode = Output(Operation());
    val debug_dispatcher_program_pointer = Output(UInt(8.W));

    val debug_thread_debug_output = Output(UInt(8.W));
  });

  val memory = Module(new Memory());
  val dispatcher = Module(new Dispatcher());
  val thread = Module(new Thread());

  memory.io.readPorts(0).enable := dispatcher.io.read_requested;
  memory.io.readPorts(0).address := dispatcher.io.read_program_pointer;

  memory.io.writePorts(0).enable := io.debug_memory_write;
  memory.io.writePorts(0).address := io.debug_memory_write_address;
  memory.io.writePorts(0).data := io.debug_memory_write_data;

  dispatcher.io.thread_requesting_opcode := thread.io.idle;
  dispatcher.io.thread_program_pointer := thread.io.program_pointer;
  
  // val read_ready_delayed = RegNext(dispatcher.io.read_requested, false.B);
  // dispatcher.io.read_ready := read_ready_delayed;   
  dispatcher.io.read_ready := dispatcher.io.read_requested;   
  dispatcher.io.read_opcode := Operation.safe(memory.io.readPorts(0).data(3, 0))._1;

  when(true.B) {
    printf(p"\t[Core]=====");
    printf(p"\n\t\tdispatcher.io.read_requested=${dispatcher.io.read_requested}");
    printf(p"\n\t\tdispatcher.io.read_program_pointer=${dispatcher.io.read_program_pointer}");
    printf(p"\n\t\tdispatcher.io.read_opcode=${dispatcher.io.read_opcode}");
    // printf(p"\n\t\tread_ready_delayed=${read_ready_delayed}");
    printf(p"\n\t\tdebug_dispatcher_opcode=${io.debug_dispatcher_opcode}");
    printf(p"\n\t\tdebug_dispatcher_program_pointer=${io.debug_dispatcher_program_pointer}");
    printf(p"\n\n");
  }

  io.debug_dispatcher_opcode := dispatcher.io.opcode;
  io.debug_dispatcher_program_pointer := dispatcher.io.program_pointer;

  thread.io.dispatcher_opcode_loaded := dispatcher.io.opcode_loaded;
  thread.io.dispatcher_program_pointer := dispatcher.io.program_pointer;
  thread.io.operation := dispatcher.io.opcode;
  thread.io.immediate_a := 2.U(8.W);
  thread.io.immediate_b := 3.U(8.W);

  io.debug_thread_debug_output := thread.io.debug_output;
}
