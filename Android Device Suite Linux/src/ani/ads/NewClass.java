/*
 * Copyright 2016 Aniruddha Sarkar.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ani.ads;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * Class for testing purposes
 * @author ani
 * 
 */


public class NewClass {
    public static void main(String a[]){
        try {
            Engine eng=new Engine();
            System.out.println(eng.run(eng.adb,"shell","pm","list","packages").contains("eu.chainfire.adbd"));
        } catch (IOException ex) {
            Logger.getLogger(NewClass.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
