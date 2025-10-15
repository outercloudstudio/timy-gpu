import chisel3._
import chisel3.util._
import _root_.circt.stage.ChiselStage

class Memory extends Module {
  val io = IO(new SRAMInterface(1024, UInt(8.W), 1, 1));

  val memory = SRAM(1024, UInt(8.W), 1, 1);
}
