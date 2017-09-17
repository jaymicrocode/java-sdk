/*
 * Copyright 2017 IBM Corp. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */
package com.ibm.watson.developer_cloud.visual_recognition.v3;

import com.ibm.watson.developer_cloud.http.RequestBuilder;
import com.ibm.watson.developer_cloud.http.ServiceCall;
import com.ibm.watson.developer_cloud.service.WatsonService;
import com.ibm.watson.developer_cloud.util.RequestUtils;
import com.ibm.watson.developer_cloud.util.ResponseConverterUtils;
import com.ibm.watson.developer_cloud.util.Validator;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.AddCollectionImageOptions;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.ClassifiedImages;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.Classifier;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.Classifiers;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.ClassifyOptions;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.Collection;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.CollectionImage;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.CollectionImages;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.Collections;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.CreateClassifierOptions;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.CreateCollectionOptions;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.DeleteClassifierOptions;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.DeleteCollectionImageOptions;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.DeleteCollectionOptions;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.DeleteImageMetadataOptions;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.DetectFacesOptions;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.DetectedFaces;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.FindSimilarImagesOptions;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.GetClassifierOptions;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.GetCollectionImageOptions;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.GetCollectionOptions;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.ListClassifiersOptions;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.ListCollectionImagesOptions;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.ListCollectionsOptions;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.SimilarImages;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.UpdateClassifierOptions;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import java.io.File;

/**
 * **Important**: As of September 8, 2017, the beta period for Similarity Search is closed. For more information, see
 * [Visual Recognition API – Similarity Search
 * Update](https://www.ibm.com/blogs/bluemix/2017/08/visual-recognition-api-similarity-search-update).
 *
 * The IBM Watson Visual Recognition service uses deep learning algorithms to identify scenes, objects, and faces in
 * images you upload to the service. You can create and train a custom classifier to identify subjects that suit your
 * needs.
 *
 * **Tip**: To test calls to the **Custom classifiers** methods with the API explorer, provide your `api_key` from your
 * Bluemix service instance.
 *
 * @version v3
 * @see <a href="http://www.ibm.com/watson/developercloud/visual-recognition.html">Visual Recognition</a>
 */
public class VisualRecognition extends WatsonService {

  private static final String SERVICE_NAME = "visual_recognition";
  private static final String URL = "https://gateway-a.watsonplatform.net/visual-recognition/api";

  private String versionDate;

  /** The Constant VERSION_DATE_2016_05_20. */
  public static final String VERSION_DATE_2016_05_20 = "2016-05-20";

  /**
   * Instantiates a new `VisualRecognition`.
   *
   * @param versionDate The version date (yyyy-MM-dd) of the REST API to use. Specifying this value will keep your API
   *          calls from failing when the service introduces breaking changes.
   */
  public VisualRecognition(String versionDate) {
    super(SERVICE_NAME);
    if ((getEndPoint() == null) || getEndPoint().isEmpty()) {
      setEndPoint(URL);
    }

    Validator.isTrue((versionDate != null) && !versionDate.isEmpty(),
        "'version cannot be null. Use " + VERSION_DATE_2016_05_20);

    this.versionDate = versionDate;
  }

  /**
   * Instantiates a new `VisualRecognition` with API Key.
   *
   * @param versionDate The version date (yyyy-MM-dd) of the REST API to use. Specifying this value will keep your API
   *          calls from failing when the service introduces breaking changes.
   * @param apiKey the API Key
   */
  public VisualRecognition(String versionDate, String apiKey) {
    this(versionDate);
    setApiKey(apiKey);
  }

  /*
   * (non-Javadoc)
   */
  @Override
  protected void setAuthentication(okhttp3.Request.Builder builder) {
    if (getApiKey() == null) {
      throw new IllegalArgumentException("api_key needs to be specified. Use setApiKey()");
    }
    final okhttp3.HttpUrl url = okhttp3.HttpUrl.parse(builder.build().url().toString());
    if ((url.query() == null) || url.query().isEmpty()) {
      builder.url(builder.build().url() + "?api_key=" + getApiKey());
    } else {
      builder.url(builder.build().url() + "&api_key=" + getApiKey());
    }
  }

  /**
   * Classify images.
   *
   * @param classifyOptions the {@link ClassifyOptions} containing the options for the call
   * @return the {@link ClassifiedImages} with the response
   */
  public ServiceCall<ClassifiedImages> classify(ClassifyOptions classifyOptions) {
    Validator.notNull(classifyOptions, "classifyOptions cannot be null");
    Validator.isTrue((classifyOptions.imagesFile() != null) || (classifyOptions.parameters() != null),
        "At least one of imagesFile or parameters must be supplied.");
    RequestBuilder builder = RequestBuilder.post("/v3/classify");
    builder.query(VERSION, versionDate);
    if (classifyOptions.acceptLanguage() != null) {
      builder.header("Accept-Language", classifyOptions.acceptLanguage());
    }
    MultipartBody.Builder multipartBuilder = new MultipartBody.Builder();
    multipartBuilder.setType(MultipartBody.FORM);
    if (classifyOptions.imagesFile() != null) {
      RequestBody imagesFileBody = RequestUtils.inputStreamBody(classifyOptions.imagesFile(), classifyOptions
          .imagesFileContentType());
      multipartBuilder.addFormDataPart("images_file", classifyOptions.imagesFilename(), imagesFileBody);
    }
    if (classifyOptions.parameters() != null) {
      multipartBuilder.addFormDataPart("parameters", classifyOptions.parameters());
    }
    builder.body(multipartBuilder.build());
    return createServiceCall(builder.build(), ResponseConverterUtils.getObject(ClassifiedImages.class));
  }

  /**
   * Detect faces in an image.
   *
   * @param detectFacesOptions the {@link DetectFacesOptions} containing the options for the call
   * @return the {@link DetectedFaces} with the response
   */
  public ServiceCall<DetectedFaces> detectFaces(DetectFacesOptions detectFacesOptions) {
    Validator.notNull(detectFacesOptions, "detectFacesOptions cannot be null");
    Validator.isTrue((detectFacesOptions.imagesFile() != null) || (detectFacesOptions.parameters() != null),
        "At least one of imagesFile or parameters must be supplied.");
    RequestBuilder builder = RequestBuilder.post("/v3/detect_faces");
    builder.query(VERSION, versionDate);
    MultipartBody.Builder multipartBuilder = new MultipartBody.Builder();
    multipartBuilder.setType(MultipartBody.FORM);
    if (detectFacesOptions.imagesFile() != null) {
      RequestBody imagesFileBody = RequestUtils.inputStreamBody(detectFacesOptions.imagesFile(), detectFacesOptions
          .imagesFileContentType());
      multipartBuilder.addFormDataPart("images_file", detectFacesOptions.imagesFilename(), imagesFileBody);
    }
    if (detectFacesOptions.parameters() != null) {
      multipartBuilder.addFormDataPart("parameters", detectFacesOptions.parameters());
    }
    builder.body(multipartBuilder.build());
    return createServiceCall(builder.build(), ResponseConverterUtils.getObject(DetectedFaces.class));
  }

  /**
   * Create a classifier.
   *
   * @param createClassifierOptions the {@link CreateClassifierOptions} containing the options for the call
   * @return the {@link Classifier} with the response
   */
  public ServiceCall<Classifier> createClassifier(CreateClassifierOptions createClassifierOptions) {
    Validator.notNull(createClassifierOptions, "createClassifierOptions cannot be null");
    RequestBuilder builder = RequestBuilder.post("/v3/classifiers");
    builder.query(VERSION, versionDate);
    MultipartBody.Builder multipartBuilder = new MultipartBody.Builder();
    multipartBuilder.setType(MultipartBody.FORM);
    multipartBuilder.addFormDataPart("name", createClassifierOptions.name());
    // Classes
    for (String className : createClassifierOptions.classNames()) {
      String dataName = className + "_positive_examples";
      File positiveExamples = createClassifierOptions.positiveExamplesByClassName(className);
      RequestBody body = RequestUtils.fileBody(positiveExamples, "application/octet-stream");
      multipartBuilder.addFormDataPart(dataName, positiveExamples.getName(), body);
    }
    if (createClassifierOptions.negativeExamples() != null) {
      RequestBody negativeExamplesBody = RequestUtils.inputStreamBody(createClassifierOptions.negativeExamples(),
          "application/octet-stream");
      multipartBuilder.addFormDataPart("negative_examples", createClassifierOptions.negativeExamplesFilename(),
          negativeExamplesBody);
    }
    builder.body(multipartBuilder.build());
    return createServiceCall(builder.build(), ResponseConverterUtils.getObject(Classifier.class));
  }

  /**
   * Delete a custom classifier.
   *
   * @param deleteClassifierOptions the {@link DeleteClassifierOptions} containing the options for the call
   * @return the service call
   */
  public ServiceCall<Void> deleteClassifier(DeleteClassifierOptions deleteClassifierOptions) {
    Validator.notNull(deleteClassifierOptions, "deleteClassifierOptions cannot be null");
    RequestBuilder builder = RequestBuilder.delete(String.format("/v3/classifiers/%s", deleteClassifierOptions
        .classifierId()));
    builder.query(VERSION, versionDate);
    return createServiceCall(builder.build(), ResponseConverterUtils.getVoid());
  }

  /**
   * Retrieve information about a custom classifier.
   *
   * @param getClassifierOptions the {@link GetClassifierOptions} containing the options for the call
   * @return the {@link Classifier} with the response
   */
  public ServiceCall<Classifier> getClassifier(GetClassifierOptions getClassifierOptions) {
    Validator.notNull(getClassifierOptions, "getClassifierOptions cannot be null");
    RequestBuilder builder = RequestBuilder.get(String.format("/v3/classifiers/%s", getClassifierOptions
        .classifierId()));
    builder.query(VERSION, versionDate);
    return createServiceCall(builder.build(), ResponseConverterUtils.getObject(Classifier.class));
  }

  /**
   * Retrieve a list of custom classifiers.
   *
   * @param listClassifiersOptions the {@link ListClassifiersOptions} containing the options for the call
   * @return the {@link Classifiers} with the response
   */
  public ServiceCall<Classifiers> listClassifiers(ListClassifiersOptions listClassifiersOptions) {
    RequestBuilder builder = RequestBuilder.get("/v3/classifiers");
    builder.query(VERSION, versionDate);
    if (listClassifiersOptions != null) {
      if (listClassifiersOptions.verbose() != null) {
        builder.query("verbose", String.valueOf(listClassifiersOptions.verbose()));
      }
    }
    return createServiceCall(builder.build(), ResponseConverterUtils.getObject(Classifiers.class));
  }

  /**
   * Update a classifier.
   *
   * @param updateClassifierOptions the {@link UpdateClassifierOptions} containing the options for the call
   * @return the {@link Classifier} with the response
   */
  public ServiceCall<Classifier> updateClassifier(UpdateClassifierOptions updateClassifierOptions) {
    Validator.notNull(updateClassifierOptions, "updateClassifierOptions cannot be null");
    RequestBuilder builder = RequestBuilder.post(String.format("/v3/classifiers/%s", updateClassifierOptions
        .classifierId()));
    builder.query(VERSION, versionDate);
    MultipartBody.Builder multipartBuilder = new MultipartBody.Builder();
    multipartBuilder.setType(MultipartBody.FORM);
    // Classes
    for (String className : updateClassifierOptions.classNames()) {
      String dataName = className + "_positive_examples";
      File positiveExamples = updateClassifierOptions.positiveExamplesByClassName(className);
      RequestBody body = RequestUtils.fileBody(positiveExamples, "application/octet-stream");
      multipartBuilder.addFormDataPart(dataName, positiveExamples.getName(), body);
    }
    if (updateClassifierOptions.negativeExamples() != null) {
      RequestBody negativeExamplesBody = RequestUtils.inputStreamBody(updateClassifierOptions.negativeExamples(),
          "application/octet-stream");
      multipartBuilder.addFormDataPart("negative_examples", updateClassifierOptions.negativeExamplesFilename(),
          negativeExamplesBody);
    }
    builder.body(multipartBuilder.build());
    return createServiceCall(builder.build(), ResponseConverterUtils.getObject(Classifier.class));
  }

  /**
   * Create a new collection - beta.
   *
   * Create a new collection of images to search. You can create a maximum of 5 collections.
   *
   * @param createCollectionOptions the {@link CreateCollectionOptions} containing the options for the call
   * @return the {@link Collection} with the response
   */
  public ServiceCall<Collection> createCollection(CreateCollectionOptions createCollectionOptions) {
    Validator.notNull(createCollectionOptions, "createCollectionOptions cannot be null");
    RequestBuilder builder = RequestBuilder.post("/v3/collections");
    builder.query(VERSION, versionDate);
    MultipartBody.Builder multipartBuilder = new MultipartBody.Builder();
    multipartBuilder.setType(MultipartBody.FORM);
    multipartBuilder.addFormDataPart("name", createCollectionOptions.name());
    if (createCollectionOptions.disregard() != null) {
      RequestBody disregardBody = RequestUtils.inputStreamBody(createCollectionOptions.disregard(),
          "application/octet-stream");
      multipartBuilder.addFormDataPart("disregard", createCollectionOptions.disregardFilename(), disregardBody);
    }
    builder.body(multipartBuilder.build());
    return createServiceCall(builder.build(), ResponseConverterUtils.getObject(Collection.class));
  }

  /**
   * Find similar images - beta.
   *
   * @param findSimilarImagesOptions the {@link FindSimilarImagesOptions} containing the options for the call
   * @return the {@link SimilarImages} with the response
   */
  public ServiceCall<SimilarImages> findSimilarImages(FindSimilarImagesOptions findSimilarImagesOptions) {
    Validator.notNull(findSimilarImagesOptions, "findSimilarImagesOptions cannot be null");
    RequestBuilder builder = RequestBuilder.post(String.format("/v3/collections/%s/find_similar",
        findSimilarImagesOptions.collectionId()));
    builder.query(VERSION, versionDate);
    if (findSimilarImagesOptions.limit() != null) {
      builder.query("limit", String.valueOf(findSimilarImagesOptions.limit()));
    }
    MultipartBody.Builder multipartBuilder = new MultipartBody.Builder();
    multipartBuilder.setType(MultipartBody.FORM);
    RequestBody imageFileBody = RequestUtils.inputStreamBody(findSimilarImagesOptions.imageFile(),
        findSimilarImagesOptions.imageFileContentType());
    multipartBuilder.addFormDataPart("image_file", findSimilarImagesOptions.imageFilename(), imageFileBody);
    builder.body(multipartBuilder.build());
    return createServiceCall(builder.build(), ResponseConverterUtils.getObject(SimilarImages.class));
  }

  /**
   * Retrieve collection details - beta.
   *
   * Retrieve information about a specific collection. Only user-created collections can be specified.
   *
   * @param getCollectionOptions the {@link GetCollectionOptions} containing the options for the call
   * @return the {@link Collection} with the response
   */
  public ServiceCall<Collection> getCollection(GetCollectionOptions getCollectionOptions) {
    Validator.notNull(getCollectionOptions, "getCollectionOptions cannot be null");
    RequestBuilder builder = RequestBuilder.get(String.format("/v3/collections/%s", getCollectionOptions
        .collectionId()));
    builder.query(VERSION, versionDate);
    return createServiceCall(builder.build(), ResponseConverterUtils.getObject(Collection.class));
  }

  /**
   * List all custom collections - beta.
   *
   * @param listCollectionsOptions the {@link ListCollectionsOptions} containing the options for the call
   * @return the {@link Collections} with the response
   */
  public ServiceCall<Collections> listCollections(ListCollectionsOptions listCollectionsOptions) {
    RequestBuilder builder = RequestBuilder.get("/v3/collections");
    builder.query(VERSION, versionDate);
    if (listCollectionsOptions != null) {
    }
    return createServiceCall(builder.build(), ResponseConverterUtils.getObject(Collections.class));
  }

  /**
   * Add images to a collection - beta.
   *
   * @param addCollectionImageOptions the {@link AddCollectionImageOptions} containing the options for the call
   * @return the {@link CollectionImages} with the response
   */
  public ServiceCall<CollectionImages> addCollectionImage(AddCollectionImageOptions addCollectionImageOptions) {
    Validator.notNull(addCollectionImageOptions, "addCollectionImageOptions cannot be null");
    RequestBuilder builder = RequestBuilder.post(String.format("/v3/collections/%s/images", addCollectionImageOptions
        .collectionId()));
    builder.query(VERSION, versionDate);
    MultipartBody.Builder multipartBuilder = new MultipartBody.Builder();
    multipartBuilder.setType(MultipartBody.FORM);
    RequestBody imageFileBody = RequestUtils.inputStreamBody(addCollectionImageOptions.imageFile(),
        addCollectionImageOptions.imageFileContentType());
    multipartBuilder.addFormDataPart("image_file", addCollectionImageOptions.imageFilename(), imageFileBody);
    if (addCollectionImageOptions.metadata() != null) {
      RequestBody metadataBody = RequestUtils.inputStreamBody(addCollectionImageOptions.metadata(),
          "application/octet-stream");
      multipartBuilder.addFormDataPart("metadata", addCollectionImageOptions.metadataFilename(), metadataBody);
    }
    builder.body(multipartBuilder.build());
    return createServiceCall(builder.build(), ResponseConverterUtils.getObject(CollectionImages.class));
  }

  /**
   * Delete a collection - beta.
   *
   * @param deleteCollectionOptions the {@link DeleteCollectionOptions} containing the options for the call
   * @return the service call
   */
  public ServiceCall<Void> deleteCollection(DeleteCollectionOptions deleteCollectionOptions) {
    Validator.notNull(deleteCollectionOptions, "deleteCollectionOptions cannot be null");
    RequestBuilder builder = RequestBuilder.delete(String.format("/v3/collections/%s", deleteCollectionOptions
        .collectionId()));
    builder.query(VERSION, versionDate);
    return createServiceCall(builder.build(), ResponseConverterUtils.getVoid());
  }

  /**
   * Delete an image - beta.
   *
   * @param deleteCollectionImageOptions the {@link DeleteCollectionImageOptions} containing the options for the call
   * @return the service call
   */
  public ServiceCall<Void> deleteCollectionImage(DeleteCollectionImageOptions deleteCollectionImageOptions) {
    Validator.notNull(deleteCollectionImageOptions, "deleteCollectionImageOptions cannot be null");
    RequestBuilder builder = RequestBuilder.delete(String.format("/v3/collections/%s/images/%s",
        deleteCollectionImageOptions.collectionId(), deleteCollectionImageOptions.imageId()));
    builder.query(VERSION, versionDate);
    return createServiceCall(builder.build(), ResponseConverterUtils.getVoid());
  }

  /**
   * Delete image metadata - beta.
   *
   * @param deleteImageMetadataOptions the {@link DeleteImageMetadataOptions} containing the options for the call
   * @return the service call
   */
  public ServiceCall<Void> deleteImageMetadata(DeleteImageMetadataOptions deleteImageMetadataOptions) {
    Validator.notNull(deleteImageMetadataOptions, "deleteImageMetadataOptions cannot be null");
    RequestBuilder builder = RequestBuilder.delete(String.format("/v3/collections/%s/images/%s/metadata",
        deleteImageMetadataOptions.collectionId(), deleteImageMetadataOptions.imageId()));
    builder.query(VERSION, versionDate);
    return createServiceCall(builder.build(), ResponseConverterUtils.getVoid());
  }

  /**
   * List image details - beta.
   *
   * @param getCollectionImageOptions the {@link GetCollectionImageOptions} containing the options for the call
   * @return the {@link CollectionImage} with the response
   */
  public ServiceCall<CollectionImage> getCollectionImage(GetCollectionImageOptions getCollectionImageOptions) {
    Validator.notNull(getCollectionImageOptions, "getCollectionImageOptions cannot be null");
    RequestBuilder builder = RequestBuilder.get(String.format("/v3/collections/%s/images/%s", getCollectionImageOptions
        .collectionId(), getCollectionImageOptions.imageId()));
    builder.query(VERSION, versionDate);
    return createServiceCall(builder.build(), ResponseConverterUtils.getObject(CollectionImage.class));
  }

  /**
   * List 100 images in a collection - beta.
   *
   * @param listCollectionImagesOptions the {@link ListCollectionImagesOptions} containing the options for the call
   * @return the {@link CollectionImages} with the response
   */
  public ServiceCall<CollectionImages> listCollectionImages(ListCollectionImagesOptions listCollectionImagesOptions) {
    Validator.notNull(listCollectionImagesOptions, "listCollectionImagesOptions cannot be null");
    RequestBuilder builder = RequestBuilder.get(String.format("/v3/collections/%s/images", listCollectionImagesOptions
        .collectionId()));
    builder.query(VERSION, versionDate);
    return createServiceCall(builder.build(), ResponseConverterUtils.getObject(CollectionImages.class));
  }

}
