#!/bin/bash
trap "trap - SIGTERM && kill -- -$$" SIGINT SIGTERM EXIT
browser-sync start --server --startPath 'jsShell/index-dev.html' --files 'jsShell/*.html' 'jsShell/art/*.png' 'jsShell/target/scala-2.12/*.js' &
sbt
