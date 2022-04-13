package com.emoldino.serenity.common

import java.io.Closeable

interface ClosableJob : Closeable, Runnable
