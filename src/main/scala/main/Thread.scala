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
  
    val program_pointer = Output(UInt(8.W));
    val end_of_program = Output(Bool()); 
    val idle = Output(Bool());
    val debug_output = Output(UInt(8.W));
  })
  
  val end_of_program = RegInit(false.B);
  val idle = RegInit(true.B);

  val alu = Module(new Alu())
  alu.io.execute := false.B;
  alu.io.operation := Operation.NoOp;
  alu.io.compare := false.B;
  alu.io.rs := 0.U(8.W);
  alu.io.rt := 0.U(8.W);

  io.debug_output := alu.io.output;

  // val lsu = Module(new Lsu())

  val program_counter = Module(new ProgramCounter())
  program_counter.io.store_nzp := false.B;
  program_counter.io.nzp := 0.U(3.W);
  program_counter.io.update := false.B;
  program_counter.io.branch := false.B;
  program_counter.io.jump_location := 0.U(8.W);
  program_counter.io.target_nzp := 0.U(3.W);

  io.program_pointer := program_counter.io.program_counter;

  io.end_of_program := end_of_program;
  io.idle := idle;

  when(io.dispatcher_opcode_loaded && io.dispatcher_program_pointer === program_counter.io.program_counter) {
    when(io.operation === Operation.Add || io.operation === Operation.Sub || io.operation === Operation.Mul || io.operation === Operation.Div) {
      alu.io.execute := true.B;
      alu.io.operation := io.operation;
      alu.io.rs := io.immediate_a;
      alu.io.rt := io.immediate_b;

      program_counter.io.update := true.B;
      program_counter.io.branch := false.B;

      io.idle := false.B;
    }
  }

  when(true.B) {
    printf(p"\t[Thread]=====");
    printf(p"\n\t\tio.operation=${io.operation}");
    printf(p"\n\t\tprogram_pointer=${program_counter.io.program_counter}");
    printf(p"\n\t\tidle=${idle}");
    printf(p"\n\t\tio.idle=${io.idle}");
    printf(p"\n\t\tio.debug_output=${io.debug_output}");
    printf(p"\n\n");
  }
}
