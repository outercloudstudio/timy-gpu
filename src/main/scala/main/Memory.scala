import chisel3._
import chisel3.util._
import _root_.circt.stage.ChiselStage

class Memory extends Module {
  val io = IO(new SRAMInterface(1024, UInt(8.W), 1, 1, 0));

  val memory = SRAM(1024, UInt(8.W), 1, 1, 0);

  io.readPorts(0) <> memory.readPorts(0);
  io.writePorts(0) <> memory.writePorts(0);
}
