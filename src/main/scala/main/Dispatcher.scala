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
    val read_opcode = Input(UInt(8.W));
    val read_immediate_l = Input(UInt(8.W));
    val read_immediate_u = Input(UInt(8.W));
    
    val opcode_loaded = Output(Bool());
    val opcode = Output(Operation());
    val src_register = Output(Register());
    val dst_register = Output(Register());
    val program_pointer = Output(UInt(8.W));
  });

  io.read_program_pointer := io.thread_program_pointer;
  io.read_requested := false.B;

  val opcode_loaded = RegInit(false.B);
  io.opcode_loaded := opcode_loaded;
  val opcode = RegInit(Operation.NoOp);
  io.opcode := opcode;
  val src_register = RegInit(Register.A);
  io.src_register := src_register;
  val dst_register = RegInit(Register.B);
  io.dst_register := dst_register;
  val program_pointer = RegInit(0.U(8.W));
  io.program_pointer := program_pointer;

  when(io.thread_requesting_opcode) {
    printf(p"\t[Dispatcher]=====");
    printf(p"\n\t\tMarked read requested!\n\n");

    io.read_requested := true.B;
  }
  
  when(io.read_ready) {
    printf(p"\t[Dispatcher]=====");
    printf(p"\n\t\tRead Complete ${io.read_opcode}");
    printf(p"\n\t\tImmediate Lower ${io.read_immediate_l}");
    printf(p"\n\t\tImmediate Upper ${io.read_immediate_u}\n\n");

    val read_opcode = Operation.safe(io.read_opcode(6, 3))._1;
    opcode := read_opcode;
    io.opcode := read_opcode;
    
    val registers_code = io.read_opcode(2, 0);

    when(registers_code === 0.U) {
      src_register := Register.A;
      dst_register := Register.B;

      io.src_register := Register.A;
      io.dst_register := Register.B;
    }

    when(registers_code === 1.U) {
      src_register := Register.A;
      dst_register := Register.C;

      io.src_register := Register.A;
      io.dst_register := Register.C;
    }

    when(registers_code === 2.U) {
      src_register := Register.B;
      dst_register := Register.A;

      io.src_register := Register.B;
      io.dst_register := Register.A;
    }

    when(registers_code === 3.U) {
      src_register := Register.B;
      dst_register := Register.C;

      io.src_register := Register.B;
      io.dst_register := Register.C;
    }

    when(registers_code === 4.U) {
      src_register := Register.C;
      dst_register := Register.A;

      io.src_register := Register.C;
      io.dst_register := Register.A;
    }

    when(registers_code === 5.U) {
      src_register := Register.C;
      dst_register := Register.B;

      io.src_register := Register.C;
      io.dst_register := Register.B;
    }

    opcode_loaded := true.B;
    io.opcode_loaded := true.B;
    
    program_pointer := io.read_program_pointer;
    io.program_pointer := io.read_program_pointer;
    
    io.read_requested := false.B;
  }
}
