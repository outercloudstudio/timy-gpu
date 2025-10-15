import chisel3._
import chisel3.util._
import _root_.circt.stage.ChiselStage

class ProgramCounter extends Module {
  val io = IO(new Bundle {
    val store_nzp = Input(Bool());
    val nzp = Input(UInt(3.W));
    
    val update = Input(Bool());
    val branch = Input(Bool());
    val jump_location = Input(UInt(8.W));
    val target_nzp = Input(UInt(3.W));
  
    val program_counter = Output(UInt(8.W)); 
  })

  val nzp = RegInit(0.U(3.W));

  val program_counter = RegInit(0.U(8.W));
  io.program_counter := program_counter;

  when(io.store_nzp) {
    nzp := io.nzp;
  }

  when(io.update) {
    when(io.branch && (nzp & io.target_nzp) =/= 0.U) {
      io.program_counter := io.jump_location;
      program_counter := io.jump_location;
    }.otherwise {
      io.program_counter := program_counter + 1.U;
      program_counter := program_counter + 1.U;
    }
  }
}
