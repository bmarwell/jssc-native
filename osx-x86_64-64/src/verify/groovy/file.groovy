/*
 * GNU Lesser General Public License v3.0
 * Copyright (C) 2020
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */
def library = new File(properties['output.library.path'])

println '[INFO] Checking if built library [' + library.getAbsolutePath() + '] exists.'
if (!library.isFile() || !library.exists()) {
  throw new IllegalStateException("Library does not exist")
}

def stdOut = new StringBuilder()
def stdErr = new StringBuilder()
def proc = ('file ' + library.getAbsolutePath()).execute()
proc.consumeProcessOutput(stdOut, stdErr)
proc.waitForOrKill(1000)

// Mach-O 64-bit dynamically linked shared library x86_64

println '[INFO] Checking if built library [' + library.getAbsolutePath() + '] is Mach-O 64-bit.'
def is32bit = stdOut.toString().contains("Mach-O 64-bit")

if (!is32bit) {
  throw new IllegalStateException("Built library is not 'Mach-O 64-bit'!")
}

println '[INFO] Checking if built library [' + library.getAbsolutePath() + '] is shared library.'
def isSharedObject = stdOut.toString().contains("shared library")

if (!isSharedObject) {
  throw new IllegalStateException("Built library is not 'shared library'!")
}

println '[INFO] Checking if built library [' + library.getAbsolutePath() + '] x86_64.'
def isX8664Arch = stdOut.toString().contains("x86_64")

if (!isX8664Arch) {
  throw new IllegalStateException("Built library is not 'x86_64'! [" + stdOut.toString().trim() + "].")
}

println '[INFO] Checking if built library [' + library.getAbsolutePath() + '] is dynamically linked.'
def isDynLinked = stdOut.toString().contains("dynamically linked")

if (!isDynLinked) {
  throw new IllegalStateException("Built library is not 'dynamically linked'!")
}

return 0