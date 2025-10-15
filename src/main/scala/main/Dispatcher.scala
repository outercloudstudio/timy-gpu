import chisel3._
import chisel3.util._
import _root_.circt.stage.ChiselStage

class Dispatcher extends Module {
  val io = IO(new Bundle {
    val read_requested = Output(Bool());
    val read_address = Output(UInt(8.W));
    val read_ready = Input(Bool());
    val read_data = Input(UInt(8.W));
  });

  val memory = SRAM(1024, UInt(8.W), 2, 2);
}
