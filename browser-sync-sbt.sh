#!/bin/bash
trap "trap - SIGTERM && kill -- -$$" SIGINT SIGTERM EXIT
browser-sync start --server --startPath 'index-dev.html' --files './*.html' './webShell/target/scala-2.12/*.js' &
sbt
