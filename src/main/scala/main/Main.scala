import chisel3._
import chisel3.util._
import _root_.circt.stage.ChiselStage

object Main extends App {
    emitVerilog(new Alu(), Array("--target-dir", "generated"));
    emitVerilog(new Lsu(), Array("--target-dir", "generated"));
}