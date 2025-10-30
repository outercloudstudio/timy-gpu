import chisel3._
import chisel3.util._
import _root_.circt.stage.ChiselStage

object Operation extends ChiselEnum {
  val NoOp, MoveImmediate, MoveRegister, Load, Add, Mul, Compare, JumpE, JumpNE, End, Write = Value
}