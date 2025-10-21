import chisel3._
import chisel3.util._
import _root_.circt.stage.ChiselStage

object Register extends ChiselEnum {
  val A, B, C = Value
}