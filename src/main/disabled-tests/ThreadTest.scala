import chisel3._
import chiseltest._
import org.scalatest.flatspec.AnyFlatSpec

class ThreadTest extends AnyFlatSpec with ChiselScalatestTester {
  "Thread ALU Operations" should "work" in {
    test(new Thread) { dut =>
      dut.io.dispatcher_opcode_loaded.poke(true.B);
      dut.io.dispatcher_program_pointer.poke(0.U(8.W));
      dut.io.operation.poke(Operation.Add);
      dut.io.immediate_a.poke(2.U);
      dut.io.immediate_b.poke(3.U);

      dut.io.debug_output.expect(5.U);

      println("[ThreadTest]=====");
      dut.clock.step(1);

      dut.io.program_pointer.expect(1.U);
      

      dut.io.dispatcher_opcode_loaded.poke(true.B);
      dut.io.dispatcher_program_pointer.poke(1.U(8.W));
      dut.io.operation.poke(Operation.Mul);
      dut.io.immediate_a.poke(2.U);
      dut.io.immediate_b.poke(3.U);

      dut.io.debug_output.expect(6.U);

      println("[ThreadTest]=====");
      dut.clock.step(1);

      dut.io.program_pointer.expect(2.U);
    }
  }
}
