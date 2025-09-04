import chisel3._
import chisel3.util._
import _root_.circt.stage.ChiselStage

class Thread extends Module {
  val io = IO(new Bundle {
    val store_operation = Input(Bool());
    val operation = Input(Operation());
    val immediate_a = Input(UInt(8.W));
    val immediate_b = Input(UInt(8.W));
  
    val end_of_program = Output(Bool()); 
    val idle = Output(Bool());
  })

  val operation = RegInit(Operation.NoOp);
  val immediate_a = RegInit(0.U(8.W));
  val immediate_b = RegInit(0.U(8.W));
  
  val end_of_program = RegInit(false.B);
  val idle = RegInit(true.B);

  val alu = Module(Alu())
  val lsu = Module(Lsu())
  val program_counter = Module(ProgramCounter())

  val immediate_b = RegInit(0.U(8.W));

  io.end_of_program := end_of_program;
  io.idle := idle;

  when(io.store_operation) {
    operation := io.operation;
    immediate_a := io.immediate_a;
    immediate_b := io.immediate_b;

    when(operation === Operation.Add || operation === Operation.Sub || operation === Operation.Mul || operation === Operation.Div) {
      alu.
    }
    
  }
}
