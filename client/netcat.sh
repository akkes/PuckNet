#!/bin/sh
killall nc;
(echo "JOIN alice FF0000"; sleep 1; echo "ACK 0 X 123 Y 45"; sleep 1; echo "ACK 3 X 234"; sleep 1; echo "ACK 5 X 245") | nc -p 30001 -u 127.0.0.1 7825 &
sleep .25; (echo "JOIN bob 00FF00"; sleep 1; echo "ACK 0 X 67 Y 890") | nc -p 30002 -u 127.0.0.1 7825 &
#echo "JOIN charlie 0000FF" | nc -p 30003 -u 127.0.0.1 7825 &
#echo "JOIN david FFFFFF" | nc -p 30004 -u 127.0.0.1 7825 &
