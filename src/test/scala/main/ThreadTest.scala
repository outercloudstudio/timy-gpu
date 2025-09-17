import chisel3._
import chiseltest._
import org.scalatest.flatspec.AnyFlatSpec

class ThreadTest extends AnyFlatSpec with ChiselScalatestTester {
  "Thread ALU Operations" should "work" in {
    test(new Thread) { dut =>
      dut.io.operation_ready.poke(true.B);
      dut.io.operation.poke(Operation.Add);
      dut.io.immediate_a.poke(2.U);
      dut.io.immediate_b.poke(3.U);

      dut.clock.step(1);

      dut.io.debug_output.expect(5.U);

      dut.io.operation_ready.poke(true.B);
      dut.io.operation.poke(Operation.Mul);
      dut.io.immediate_a.poke(2.U);
      dut.io.immediate_b.poke(3.U);

      dut.clock.step(1);

      dut.io.debug_output.expect(6.U);
    
      dut.io.operation_ready.poke(true.B);
      dut.io.operation.poke(Operation.Sub);
      dut.io.immediate_a.poke(3.U);
      dut.io.immediate_b.poke(2.U);

      dut.clock.step(1);

      dut.io.debug_output.expect(1.U);

      dut.io.operation_ready.poke(true.B);
      dut.io.operation.poke(Operation.Div);
      dut.io.immediate_a.poke(6.U);
      dut.io.immediate_b.poke(3.U);

      dut.clock.step(1);

      dut.io.debug_output.expect(2.U);
    }
  }
}
