import chisel3._
import chisel3.util._
import _root_.circt.stage.ChiselStage

object LsuState extends ChiselEnum {
  val Idle, Requesting = Value
}

class Lsu extends Module {
  val io = IO(new Bundle {
    val read = Input(Bool());
    // val write = Input(Bool());

    val address = Input(UInt(8.W));
    // val value = Input(UInt(8.W));

    val read_requested = Output(Bool());
    val read_address = Output(UInt(8.W));
    val read_ready = Input(Bool());
    val read_data = Input(UInt(8.W));

    // val write_ready = Input(Bool());
    // val write_data = Output(UInt(8.W));
    // val write_valid = Output(Bool());
    // val write_address = Output(UInt(8.W));

    val state = Output(LsuState());
    val output = Output(UInt(8.W));
  })

  val state = RegInit(LsuState.Idle);
  io.state := state;

  val output = RegInit(0.U(8.W));
  io.output := output;

  val read_requested = RegInit(false.B);
  io.read_requested := read_requested;

  val read_address = RegInit(false.B);
  io.read_address := read_address;

  when(io.read) {
    switch(io.state) {
      is(LsuState.Idle) {
        state := LsuState.Requesting;
        read_requested := true.B;
        read_address := io.address;
      }

      is(LsuState.Requesting) {
        when(io.read_ready) {
          state := LsuState.Idle;
          read_requested := false.B;
          output := io.read_data;
        }
      }
    }
  }
}
