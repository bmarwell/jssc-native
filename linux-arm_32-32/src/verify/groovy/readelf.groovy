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

println '[INFO] Checking if built library [' + library.getAbsolutePath() + '] does not make use of VFP registers.'

def stdOut = new StringBuilder()
def stdErr = new StringBuilder()
def proc = ('readelf -A ' + library.getAbsolutePath()).execute()
proc.consumeProcessOutput(stdOut, stdErr)
proc.waitForOrKill(1000)
def containsVfpRegisters = stdOut.toString().contains("VFP registers")

if (containsVfpRegisters) {
  println '[ERROR] Library [' + library.getAbsolutePath() + '] makes use of VFP registers.'
  throw new IllegalStateException("Library makes use of VFP registers")
}

return 0
