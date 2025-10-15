import chisel3._
import chiseltest._
import org.scalatest.flatspec.AnyFlatSpec

class CoreTest extends AnyFlatSpec with ChiselScalatestTester {
  "Core" should "work" in {
    test(new Core) { dut =>
      dut.io.debug_memory_write.poke(true.B);
      dut.io.debug_memory_write_address.poke(0.U(8.W));
      dut.io.debug_memory_write_data.poke(1.U(8.W));

      dut.clock.step(1);

      dut.io.debug_dispatcher_thread_requesting_opcode.poke(true.B);
      dut.io.debug_dispatcher_thread_program_pointer.poke(0.U(8.W));
    
      dut.clock.step(1);

      dut.clock.step(1);
      
      dut.clock.step(1);

      dut.io.debug_dispatcher_opcode.expect(Operation.Add);
      dut.io.debug_dispatcher_program_pointer.expect(0.U(8.W));
    }
  }
}
