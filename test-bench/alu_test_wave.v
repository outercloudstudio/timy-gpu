`timescale 1ns/1ps

module Alu_tb;
  reg        clock;
  reg        reset;
  reg        io_execute;
  reg  [3:0] io_operation;
  reg        io_compare;
  reg  [7:0] io_rs;
  reg  [7:0] io_rt;
  wire [7:0] io_output;

  // Instantiate the DUT (Device Under Test)
  Alu dut (
    .clock(clock),
    .reset(reset),
    .io_execute(io_execute),
    .io_operation(io_operation),
    .io_compare(io_compare),
    .io_rs(io_rs),
    .io_rt(io_rt),
    .io_output(io_output)
  );

  // Clock generation (10 ns period)
  always #5 clock = ~clock;

  // Test procedure
  initial begin
    // Setup waveform dumping for GTKWave
    $dumpfile("Alu_tb.vcd");       // output file name
    $dumpvars(0, Alu_tb);          // dump all signals in this testbench (and DUT)

    $display("Starting ALU testbench...");
    $display("time | execute compare op rs rt | output");
    $display("------------------------------------------");

    // Initialize signals
    clock = 0;
    reset = 1;
    io_execute = 0;
    io_compare = 0;
    io_operation = 0;
    io_rs = 0;
    io_rt = 0;

    // Apply reset
    #10 reset = 0;

    // Run tests
    run_all_tests();

    $display("All tests completed. Waveform written to Alu_tb.vcd");
    $finish;
  end

  // Task to apply one test vector
  task apply_test;
    input [3:0] op;
    input cmp;
    input [7:0] rs;
    input [7:0] rt;
    begin
      io_operation = op;
      io_compare = cmp;
      io_rs = rs;
      io_rt = rt;
      io_execute = 1;
      #10; // wait a clock cycle
      $display("%4t | %b       %b       %h  %h %h | %h",
               $time, io_execute, io_compare, io_operation, io_rs, io_rt, io_output);
      io_execute = 0;
      #10;
    end
  endtask

  // Task to test all operations and comparison modes
  task run_all_tests;
    integer i;
    begin
      // Test several data patterns
      for (i = 0; i < 4; i = i + 1) begin
        case (i)
          0: begin io_rs = 8'd10;  io_rt = 8'd3;   end
          1: begin io_rs = 8'd3;   io_rt = 8'd10;  end
          2: begin io_rs = 8'd5;   io_rt = 8'd5;   end
          3: begin io_rs = 8'd255; io_rt = 8'd2;   end
        endcase

        // Comparison mode (io_compare = 1)
        apply_test(4'd0, 1'b1, io_rs, io_rt); // compare

        // Arithmetic operations (io_compare = 0)
        apply_test(4'd4, 1'b0, io_rs, io_rt); // addition
        apply_test(4'd5, 1'b0, io_rs, io_rt); // multiplication
      end
    end
  endtask

endmodule

