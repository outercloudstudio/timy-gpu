import chisel3._
import chiseltest._
import org.scalatest.flatspec.AnyFlatSpec

class ProgramCounterTest extends AnyFlatSpec with ChiselScalatestTester {
  "Program Counter Increment" should "work" in {
    test(new ProgramCounter) { dut =>
      dut.io.update.poke(true.B);
      dut.io.program_counter.expect(1.U);
    }
  }

  "Program Counter Jump" should "work" in {
    test(new ProgramCounter) { dut =>
      dut.io.store_nzp.poke(true.B);
      dut.io.nzp.poke(1.U);

      dut.clock.step(1);

      dut.io.update.poke(true.B);
      dut.io.branch.poke(true.B);
      dut.io.jump_location.poke(8.U);
      dut.io.target_nzp.poke(1.U);

      dut.io.program_counter.expect(8.U);
    }
  }

  "Program Counter Jump Fail" should "work" in {
    test(new ProgramCounter) { dut =>
      dut.io.store_nzp.poke(true.B);
      dut.io.nzp.poke(1.U);

      dut.clock.step(1);

      dut.io.update.poke(true.B);
      dut.io.branch.poke(true.B);
      dut.io.jump_location.poke(8.U);
      dut.io.target_nzp.poke(2.U);

      dut.io.program_counter.expect(1.U);
    }
  }
}
