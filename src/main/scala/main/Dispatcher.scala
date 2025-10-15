import chisel3._
import chisel3.util._
import _root_.circt.stage.ChiselStage

class Dispatcher extends Module {
  val io = IO(new Bundle {
    val thread_requesting_opcode = Input(Bool());
    val thread_program_pointer = Input(UInt(8.W));

    val read_requested = Output(UInt(8.W));
    val read_program_pointer = Output(UInt(8.W));
    val read_ready = Input(Bool());
    val read_opcode = Input(Operation());
    
    val opcode = Output(Operation());
    val program_pointer = Output(UInt(8.W));
  });

  val read_requested = RegInit(0.U(8.W));
  io.read_requested := read_requested;
  val read_program_pointer = RegInit(0.U(8.W));
  io.read_program_pointer := read_program_pointer;

  val opcode = RegInit(Operation.NoOp);
  io.opcode := opcode;
  val program_pointer = RegInit(0.U(8.W));
  io.program_pointer := program_pointer;

  when(io.thread_requesting_opcode) {
    read_program_pointer := io.thread_program_pointer;
    read_requested := true.B;
  }
  
  when(io.read_ready) {
    opcode := io.read_opcode;
    program_pointer := io.read_program_pointer;
    read_requested := false.B;
  }
}
