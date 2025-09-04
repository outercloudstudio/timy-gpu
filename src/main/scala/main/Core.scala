import chisel3._
import chisel3.util._
import _root_.circt.stage.ChiselStage

class Core extends Module {
  val io = IO(new Bundle {
    val operation = Input(Operation());
    val immediate_a = Input(UInt(8.W));
    val immediate_b = Input(UInt(8.W));
  });
}
