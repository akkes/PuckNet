#!/bin/sh
echo "JOIN alice FF0000" | nc -u 127.0.0.1 7825 &
echo "JOIN bob 00FF00" | nc -u 127.0.0.1 7825 &
echo "JOIN charlie 0000FF" | nc -u 127.0.0.1 7825 &
echo "JOIN david FFFFFF" | nc -u 127.0.0.1 7825 &
