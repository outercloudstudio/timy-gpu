import chisel3._
import chiseltest._
import org.scalatest.flatspec.AnyFlatSpec

class AluTest extends AnyFlatSpec with ChiselScalatestTester {
  "Alu Add" should "work" in {
    test(new Alu) { dut =>
      dut.io.execute.poke(true.B);
      dut.io.operation.poke(Operation.Add);
      dut.io.compare.poke(false.B);
      dut.io.rs.poke(2.U);
      dut.io.rt.poke(3.U);

      dut.io.output.expect(5.U);
    }
  }
}
