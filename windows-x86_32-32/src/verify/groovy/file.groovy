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

// PE32 executable (DLL) (console) Intel 80386 (stripped to external PDB), for MS Windows

println '[INFO] Checking if built library [' + library.getAbsolutePath() + '] is PE32 executable.'
def isPE32 = stdOut.toString().contains("PE32 executable")

if (!isPE32) {
  throw new IllegalStateException("Built library is not 'PE32 executable'!")
}

println '[INFO] Checking if built library [' + library.getAbsolutePath() + '] is (DLL).'
def isSharedObject = stdOut.toString().contains("(DLL)")

if (!isSharedObject) {
  throw new IllegalStateException("Built library is not '(DLL)'!")
}

println '[INFO] Checking if built library [' + library.getAbsolutePath() + '] Intel 80386.'
def isX8632 = stdOut.toString().contains("Intel 80386")

if (!isX8632) {
  throw new IllegalStateException("Built library is not 'Intel 80386'! [" + stdOut.toString().trim() + "].")
}

println '[INFO] Checking if built library [' + library.getAbsolutePath() + '] is for MS Windows.'
def forMsWindows = stdOut.toString().contains("for MS Windows")

if (!forMsWindows) {
  throw new IllegalStateException("Built library is not 'for MS Windows'!")
}

return 0