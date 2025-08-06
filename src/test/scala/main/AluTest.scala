import chisel3._
import chiseltest._
import org.scalatest.flatspec.AnyFlatSpec

class AluTest extends AnyFlatSpec with ChiselScalatestTester {
  "Alu" should "work" in {
    test(new Alu) { dut =>
      dut.io.enable.poke(true.B);
      dut.io.operation.poke(AluOperation.Add);
      dut.io.compare.poke(false.B);
      dut.io.rs.poke(2.U);
      dut.io.rt.poke(3.U);

      dut.clock.step(1);

      dut.io.output.expect(5.U);
    }
  }
}
