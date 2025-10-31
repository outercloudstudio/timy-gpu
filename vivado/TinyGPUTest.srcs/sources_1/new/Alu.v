`timescale 1ns / 1ps
//////////////////////////////////////////////////////////////////////////////////
// Company: 
// Engineer: 
// 
// Create Date: 10/30/2025 11:55:56 PM
// Design Name: 
// Module Name: Alu
// Project Name: 
// Target Devices: 
// Tool Versions: 
// Description: 
// 
// Dependencies: 
// 
// Revision:
// Revision 0.01 - File Created
// Additional Comments:
// 
//////////////////////////////////////////////////////////////////////////////////



module Alu(
  input        clock,
  input        reset,
  input        io_execute, // @[src/main/scala/main/Alu.scala 6:14]
  input  [3:0] io_operation, // @[src/main/scala/main/Alu.scala 6:14]
  input        io_compare, // @[src/main/scala/main/Alu.scala 6:14]
  input  [7:0] io_rs, // @[src/main/scala/main/Alu.scala 6:14]
  input  [7:0] io_rt, // @[src/main/scala/main/Alu.scala 6:14]
  output [7:0] io_output // @[src/main/scala/main/Alu.scala 6:14]
);
  wire  gt = io_rs > io_rt; // @[src/main/scala/main/Alu.scala 22:22]
  wire  eq = io_rs == io_rt; // @[src/main/scala/main/Alu.scala 23:22]
  wire  lt = io_rs < io_rt; // @[src/main/scala/main/Alu.scala 24:22]
  wire [7:0] _io_output_T = {5'h0,gt,eq,lt}; // @[src/main/scala/main/Alu.scala 26:23]
  wire [7:0] _io_output_T_2 = io_rs + io_rt; // @[src/main/scala/main/Alu.scala 30:30]
  wire [15:0] _io_output_T_3 = io_rs * io_rt; // @[src/main/scala/main/Alu.scala 34:30]
  wire [15:0] _GEN_0 = 4'h5 == io_operation ? _io_output_T_3 : 16'h0; // @[src/main/scala/main/Alu.scala 18:13 28:28 34:21]
  wire [15:0] _GEN_1 = 4'h4 == io_operation ? {{8'd0}, _io_output_T_2} : _GEN_0; // @[src/main/scala/main/Alu.scala 28:28 30:21]
  wire [15:0] _GEN_2 = io_compare ? {{8'd0}, _io_output_T} : _GEN_1; // @[src/main/scala/main/Alu.scala 21:22 26:17]
  wire [15:0] _GEN_3 = io_execute ? _GEN_2 : 16'h0; // @[src/main/scala/main/Alu.scala 18:13 20:20]
  assign io_output = _GEN_3[7:0];
endmodule