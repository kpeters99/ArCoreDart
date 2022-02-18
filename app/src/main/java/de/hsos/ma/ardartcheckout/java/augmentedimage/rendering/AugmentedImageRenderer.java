/*
 * Copyright 2018 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.hsos.ma.ardartcheckout.java.augmentedimage.rendering;

import android.content.Context;
import android.opengl.Matrix;

import com.google.ar.core.Anchor;
import com.google.ar.core.AugmentedImage;
import com.google.ar.core.Pose;

import de.hsos.ma.ardartcheckout.java.common.rendering.ObjectRenderer;
import de.hsos.ma.ardartcheckout.java.common.rendering.ObjectRenderer.BlendMode;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/** Renders an augmented image. */
public class AugmentedImageRenderer {
  private static final String TAG = "AugmentedImageRenderer";

  private static final float TINT_INTENSITY = 0.1f;
  private static final float TINT_ALPHA = 1.0f;
  private static final int[] TINT_COLORS_HEX = {
    0x000000, 0xF44336, 0xE91E63, 0x9C27B0, 0x673AB7, 0x3F51B5, 0x2196F3, 0x03A9F4, 0x00BCD4,
    0x009688, 0x4CAF50, 0x8BC34A, 0xCDDC39, 0xFFEB3B, 0xFFC107, 0xFF9800,
  };

  List<Pose> dartPoseList;

  Pose dart1Pose;
  Pose dart2Pose;
  Pose dart3Pose;

  private final ObjectRenderer dart1 = new ObjectRenderer();
  private final ObjectRenderer dart2 = new ObjectRenderer();
  private final ObjectRenderer dart3 = new ObjectRenderer();

  private final ObjectRenderer imageFrameUpperLeft = new ObjectRenderer();
  private final ObjectRenderer imageFrameUpperRight = new ObjectRenderer();
  private final ObjectRenderer imageFrameLowerLeft = new ObjectRenderer();
  private final ObjectRenderer imageFrameLowerRight = new ObjectRenderer();



  public AugmentedImageRenderer() {}

  public void createOnGlThread(Context context) throws IOException {

    dart1.createOnGlThread(
            context, "models/andy.obj", "models/andy.png");
    dart1.setMaterialProperties(0.0f, 3.5f, 1.0f, 6.0f);

    dart1.setBlendMode(BlendMode.AlphaBlending);
    dart2.createOnGlThread(
            context, "models/andy.obj", "models/andy.png");
    dart2.setMaterialProperties(0.0f, 3.5f, 1.0f, 6.0f);
    dart2.setBlendMode(BlendMode.AlphaBlending);

    dart3.createOnGlThread(
            context, "models/Arrow5.obj", "models/Arrow5Normal.png");
    dart3.setMaterialProperties(0.0f, 3.5f, 1.0f, 6.0f);
    dart3.setBlendMode(BlendMode.AlphaBlending);
  }

  public void draw(
      float[] viewMatrix,
      float[] projectionMatrix,
      AugmentedImage augmentedImage,
      Anchor centerAnchor,
      float[] colorCorrectionRgba, ArrayList<Integer> checkout) {
    float[] tintColor =
        convertHexToColor(TINT_COLORS_HEX[augmentedImage.getIndex() % TINT_COLORS_HEX.length]);


    Pose[] dartPoses = {
            Pose.makeTranslation(
                    0.0f * augmentedImage.getExtentX(),
                    0.0f,
                    0.0f * augmentedImage.getExtentZ()),      // Bullseye
            Pose.makeTranslation(
                    0.1f * augmentedImage.getExtentX(),
                    0.0f,
                    -0.35f * augmentedImage.getExtentZ()),    //  1
            Pose.makeTranslation(
                    0.2f * augmentedImage.getExtentX(),
                    0.0f,
                    0.3f * augmentedImage.getExtentZ()),      //  2
            Pose.makeTranslation(
                    0.0f * augmentedImage.getExtentX(),
                    0.0f,
                    0.4f * augmentedImage.getExtentZ()),      //  3
            Pose.makeTranslation(
                    0.27f * augmentedImage.getExtentX(),
                    0.0f,
                    -0.2f * augmentedImage.getExtentZ()),     //  4
            Pose.makeTranslation(
                    -0.1f * augmentedImage.getExtentX(),
                    0.0f,
                    -0.35f * augmentedImage.getExtentZ()),    //  5
            Pose.makeTranslation(
                    0.4f * augmentedImage.getExtentX(),
                    0.0f,
                    0.0f * augmentedImage.getExtentZ()),      //  6
            Pose.makeTranslation(
                    -0.2f * augmentedImage.getExtentX(),
                    0.0f,
                    0.3f * augmentedImage.getExtentZ()),      //  7
            Pose.makeTranslation(
                    -0.35f * augmentedImage.getExtentX(),
                    0.0f,
                    0.1f * augmentedImage.getExtentZ()),      //  8
            Pose.makeTranslation(
                    -0.27f * augmentedImage.getExtentX(),
                    0.0f,
                    -0.2f * augmentedImage.getExtentZ()),     //  9
            Pose.makeTranslation(
                    0.35f * augmentedImage.getExtentX(),
                    0.0f,
                    0.1f * augmentedImage.getExtentZ()),      // 10
            Pose.makeTranslation(
                    -0.4f * augmentedImage.getExtentX(),
                    0.0f,
                    0.0f * augmentedImage.getExtentZ()),      // 11
            Pose.makeTranslation(
                    -0.2f * augmentedImage.getExtentX(),
                    0.0f,
                    -0.3f * augmentedImage.getExtentZ()),     // 12
            Pose.makeTranslation(
                    0.35f * augmentedImage.getExtentX(),
                    0.0f,
                    -0.1f * augmentedImage.getExtentZ()),     // 13
            Pose.makeTranslation(
                    -0.35f * augmentedImage.getExtentX(),
                    0.0f,
                    -0.1f * augmentedImage.getExtentZ()),     // 14
            Pose.makeTranslation(
                    0.27f * augmentedImage.getExtentX(),
                    0.0f,
                    0.2f * augmentedImage.getExtentZ()),      // 15
            Pose.makeTranslation(
                    -0.27f * augmentedImage.getExtentX(),
                    0.0f,
                    0.2f * augmentedImage.getExtentZ()),      // 16
            Pose.makeTranslation(
                    0.1f * augmentedImage.getExtentX(),
                    0.0f,
                    0.35f * augmentedImage.getExtentZ()),     // 17
            Pose.makeTranslation(
                    0.2f * augmentedImage.getExtentX(),
                    0.0f,
                    -0.3f * augmentedImage.getExtentZ()),     // 18
            Pose.makeTranslation(
                    -0.1f * augmentedImage.getExtentX(),
                    0.0f,
                    0.35f * augmentedImage.getExtentZ()),     // 19
            Pose.makeTranslation(
                    0.0f * augmentedImage.getExtentX(),
                    0.0f,
                    -0.4f * augmentedImage.getExtentZ())      // 20
    };

    dartPoseList = Arrays.asList(dartPoses);

    Pose anchorPose = centerAnchor.getPose();

    float scaleFactor = 0.05f;
    float[] modelMatrix = new float[16];

    dart1Pose = dartPoses[checkout.get(0)];
    dart2Pose = dartPoses[checkout.get(1)];
    dart3Pose = dartPoses[checkout.get(checkout.size() - 1)];

    if(checkout.size() > 2){
      anchorPose.compose(dart1Pose).toMatrix(modelMatrix, 0);
      dart1.updateModelMatrix(modelMatrix, scaleFactor);
      dart1.draw(viewMatrix, projectionMatrix, colorCorrectionRgba, tintColor);

      anchorPose.compose(dart2Pose).toMatrix(modelMatrix, 0);
      dart2.updateModelMatrix(modelMatrix, scaleFactor);
      dart2.draw(viewMatrix, projectionMatrix, colorCorrectionRgba, tintColor);
    }
    else if(checkout.size() > 1){
      anchorPose.compose(dart1Pose).toMatrix(modelMatrix, 0);
      dart1.updateModelMatrix(modelMatrix, scaleFactor);
      dart1.draw(viewMatrix, projectionMatrix, colorCorrectionRgba, tintColor);
    }

    anchorPose.compose(dart3Pose).toMatrix(modelMatrix, 0);
    Matrix.rotateM(modelMatrix, 0, 90, 0f, 0f, -1f);
    dart3.updateModelMatrix(modelMatrix, scaleFactor/10);
    dart3.draw(viewMatrix, projectionMatrix, colorCorrectionRgba, convertHexToColor(0x0111b6));

  }


  private static float[] convertHexToColor(int colorHex) {
    // colorHex is in 0xRRGGBB format
    float red = ((colorHex & 0xFF0000) >> 16) / 255.0f * TINT_INTENSITY;
    float green = ((colorHex & 0x00FF00) >> 8) / 255.0f * TINT_INTENSITY;
    float blue = (colorHex & 0x0000FF) / 255.0f * TINT_INTENSITY;
    return new float[] {red, green, blue, TINT_ALPHA};
  }
}
