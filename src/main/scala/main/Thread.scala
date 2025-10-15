import chisel3._
import chisel3.util._
import _root_.circt.stage.ChiselStage

class Thread extends Module {
  val io = IO(new Bundle {
    val dispatcher_opcode_loaded = Input(Bool());
    val dispatcher_program_pointer = Input(UInt(8.W));

    val operation = Input(Operation());
    val immediate_a = Input(UInt(8.W));
    val immediate_b = Input(UInt(8.W));
  
    val end_of_program = Output(Bool()); 
    val idle = Output(Bool());
    val debug_output = Output(UInt(8.W));
  })

  val operation = RegInit(Operation.NoOp);
  val immediate_a = RegInit(0.U(8.W));
  val immediate_b = RegInit(0.U(8.W));
  
  val end_of_program = RegInit(false.B);
  val idle = RegInit(true.B);

  val alu = Module(new Alu())
  alu.io.execute := false.B;
  alu.io.operation := AluOperation.Add;
  alu.io.compare := false.B;
  alu.io.rs := 0.U(8.W);
  alu.io.rt := 0.U(8.W);
  // val lsu = Module(new Lsu())

  val program_counter = Module(new ProgramCounter())
  program_counter.io.store_nzp := false.B;
  program_counter.io.nzp := 0.U(3.W);
  program_counter.io.update := false.B;
  program_counter.io.branch := false.B;
  program_counter.io.jump_location := 0.U(8.W);
  program_counter.io.target_nzp := 0.U(3.W);
  
  io.debug_output := 0.U(8.W);

  io.end_of_program := end_of_program;
  io.idle := idle;

  when(io.dispatcher_opcode_loaded && io.dispatcher_program_pointer === program_counter.io.program_counter) {
    operation := io.operation;
    immediate_a := io.immediate_a;
    immediate_b := io.immediate_b;

    when(operation === Operation.Add || operation === Operation.Sub || operation === Operation.Mul || operation === Operation.Div) {
      alu.io.execute := true.B;
      
      switch(operation) {
        is (Operation.Add) {
          alu.io.operation := AluOperation.Add;
        }
        is (Operation.Sub) {
          alu.io.operation := AluOperation.Sub;
        }
        is (Operation.Mul) {
          alu.io.operation := AluOperation.Mul;
        }
        is (Operation.Div) {
          alu.io.operation := AluOperation.Div;
        }
      }

      alu.io.rs := io.immediate_a;
      alu.io.rt := io.immediate_b;
      
      io.debug_output := alu.io.output;
    }
  }
}
