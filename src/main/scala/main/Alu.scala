import chisel3._
import chisel3.util._
import _root_.circt.stage.ChiselStage

class Alu extends Module {
  val io = IO(new Bundle {
    val execute = Input(Bool());

    val operation = Input(Operation());
    val compare = Input(Bool());

    val rs = Input(UInt(8.W));
    val rt = Input(UInt(8.W));

    val output = Output(UInt(8.W));
  })

  io.output := 0.U;

  when(io.execute) {
    when(io.compare) {
      val gt = io.rs > io.rt;
      val eq = io.rs === io.rt;
      val lt = io.rs < io.rt;

      io.output := Cat(0.U(5.W), gt, eq, lt);
    }.otherwise {
      switch(io.operation) {
        is(Operation.Add) {
          io.output := io.rs + io.rt;
        }

        is(Operation.Mul) {
          io.output := io.rs * io.rt;
        }
      }
    }
  }
}
