import chisel3._
import chisel3.util._
import _root_.circt.stage.ChiselStage

object Main extends App {
    println("Generating Alu")
    emitVerilog(new Alu(), Array("--target-dir", "generated"))
}