/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014-2017 Sri Harsha Chilakapati
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.shc.silenceengine.tests;

import com.shc.silenceengine.core.SilenceEngine;
import com.shc.silenceengine.input.Keyboard;
import com.shc.silenceengine.input.Touch;

/**
 * @author Sri Harsha Chilakapati
 */
public class DialogsTest extends SilenceTest
{
    @Override
    public void update(float deltaTime)
    {
        if (Keyboard.isKeyTapped(Keyboard.KEY_ESCAPE))
            SilenceEngine.display.close();

        if (Keyboard.isKeyTapped(Keyboard.KEY_ENTER) || Touch.isFingerTapped(Touch.FINGER_0))
        {
            if (SilenceEngine.display.confirm("Do you want to run dialogs test?"))
            {
                String value = SilenceEngine.display.prompt("Enter something!");
                SilenceEngine.display.alert("You've entered " + value);

                value = SilenceEngine.display.prompt("Enter something again!!", "why not?");
                SilenceEngine.display.alert("You've entered " + value);
            }
        }
    }
}
