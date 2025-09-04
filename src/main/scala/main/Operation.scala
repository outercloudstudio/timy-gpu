import chisel3._
import chisel3.util._
import _root_.circt.stage.ChiselStage

object Operation extends ChiselEnum {
  val NoOp, Add, Sub, Mul, Div, JumpE, JumpNE, Compare, End, Load, Write = Value
}