`timescale 1ns / 1ps
//////////////////////////////////////////////////////////////////////////////////
// Company: 
// Engineer: 
// 
// Create Date: 10/31/2025 12:04:42 AM
// Design Name: 
// Module Name: top
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



module top(
    input        clk,      // FPGA clock
    input  [15:0] sw,      // 16 switches (8 for rs, 8 for rt)
    input        btnC,     // Execute
    input        btnU,     // Operation bit 0
    input        btnL,     // Operation bit 1
    input        btnR,     // Operation bit 2
    input        btnD,     // Operation bit 3
    output [15:0] led      // LEDs for output
);

    // Divide switches into two 8-bit inputs
    wire [7:0] rs = sw[15:8];
    wire [7:0] rt = sw[7:0];

    // Combine buttons into a 4-bit operation selector
    wire [3:0] operation = {btnD, btnR, btnL, btnU};

    // Wires for ALU signals
    wire [7:0] alu_out;

    // Optional: compare flag can be driven by another condition or switch
    wire compare = 1'b0;  // constant off (you can assign a button if you want)

    // Instantiate the ALU
    Alu alu_inst (
        .clock(1'b0),
        .reset(1'b0),
        .io_execute(btnC),
        .io_operation(4'b0100),
        .io_compare(compare),
        .io_rs(rs),
        .io_rt(rt),
        .io_output(alu_out)
    );

    // Display the output on LEDs
    assign led[7:0]  = alu_out; // lower 8 LEDs show ALU output
    assign led[15:8] = rs;      // optional: show rs on upper LEDs for debugging

endmodule
