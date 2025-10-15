import chisel3._
import chiseltest._
import org.scalatest.flatspec.AnyFlatSpec

class DispatcherTest extends AnyFlatSpec with ChiselScalatestTester {
  "Dispatcher" should "work" in {
    test(new Dispatcher) { dut =>
      dut.io.thread_requesting_opcode.poke(true.B);
      dut.io.thread_program_pointer.poke(1.U(8.W));

      dut.clock.step(1);

      dut.io.read_requested.expect(true.B);
      dut.io.read_program_pointer.expect(1.U(8.W));
      
      dut.clock.step(1);
    
      dut.io.read_ready.poke(true.B);
      dut.io.read_opcode.poke(Operation.Add);

      dut.clock.step(1);

      dut.io.opcode.expect(Operation.Add);
      dut.io.program_pointer.expect(1.U(8.W));
    }
  }
}
