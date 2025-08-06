//> using scala "2.13.12"
//> using dep "org.chipsalliance::chisel:6.7.0"
//> using plugin "org.chipsalliance:::chisel-plugin:6.7.0"
//> using options "-unchecked", "-deprecation", "-language:reflectiveCalls", "-feature", "-Xcheckinit", "-Xfatal-warnings", "-Ywarn-dead-code", "-Ywarn-unused", "-Ymacro-annotations"

import chisel3._
import chisel3.util._
import _root_.circt.stage.ChiselStage

object AluOperation extends ChiselEnum {
  val Add, Sub, Mul, Div = Value
}

class Alu extends Module {
  val io = IO(new Bundle {
    val enable = Input(Bool())

    val operation = Input(AluOperation())
    val compare = Input(Bool())

    val rs = Input(UInt(8.W))
    val rt = Input(UInt(8.W))

    val output = Output(UInt(8.W))
  })

  io.output := 0.U

  when(io.enable) {
    when(io.compare) {
      val gt = io.rs > io.rt
      val eq = io.rs === io.rt
      val lt = io.rs < io.rt

      io.output := Cat(0.U(5.W), gt, eq, lt);
    }.otherwise {
      switch(io.operation) {
        is(AluOperation.Add) {
          io.output := io.rs + io.rt
        }

        is(AluOperation.Sub) {
          io.output := io.rs - io.rt
        }

        is(AluOperation.Mul) {
          io.output := io.rs * io.rt
        }

        is(AluOperation.Div) {
          io.output := io.rs / io.rt
        }
      }
    }
  }
}

object Main extends App {
  println(
    ChiselStage.emitSystemVerilog(
      gen = new Alu,
      firtoolOpts = Array("-disable-all-randomization", "-strip-debug-info")
    )
  )
}
