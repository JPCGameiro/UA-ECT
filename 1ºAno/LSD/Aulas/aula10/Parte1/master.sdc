#
# LSD.TOS, March 2016
#
# timing constraints
#

#
# do timing analysis for a very pessimistic case: slow and very hot FPGA
#

set_operating_conditions 7_slow_1200mv_85c


#
# identify the 50MHz clock and any other clocks obtained from it
#

set_time_format -decimal_places 3 -unit ns
create_clock -name clock_50 -period 20.000 [get_ports clock_50]
derive_pll_clocks
derive_clock_uncertainty


#
# I/O
#
# we don't care about setup and hold times concerning "slow" I/O pins
#
# when input (to the FPGA) signals are used in sequential logic, it
# is IMPERATIVE that the first thing that is done to them inside the
# FPGA be to pass them through registers
#
# for slow I/O signals, outputs do not need to pass through registers
# (but there is no harm if they do so)
#

set_false_path -from [get_ports { aud_* i2c_* irda_rxd key[*] lcd_* ps2_* sram_* sw[*] }] -to [get_clocks clock_50]
set_false_path -from [get_clocks clock_50] -to [get_ports { aud_* hex*[*] i2c_* lcd_* led*[*] ps2_* sram_* }]
#set_false_path -from [get_clocks AUD_CLK\|altpll_component\|auto_generated\|pll1\|clk\[0\]] -to [get_ports aud_*]


#
# VGA I/O
#
# given the inverted phase relationship between vga_clk and the rest
# of the vga signals, and assuming that there are no electric signal
# propagation time mismatches of more than 8ns between the FPGA pins
# and the video DAC pins, there is no need to time-contrain the vga
# signals.
#

set_false_path -from [get_clocks clock_50] -to [get_ports vga_*]
