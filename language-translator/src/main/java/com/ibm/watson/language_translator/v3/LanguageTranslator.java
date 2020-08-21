/*
 * (C) Copyright IBM Corp. 2019, 2020.
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
package com.ibm.watson.language_translator.v3;

import com.google.gson.JsonObject;
import com.ibm.cloud.sdk.core.http.RequestBuilder;
import com.ibm.cloud.sdk.core.http.ResponseConverter;
import com.ibm.cloud.sdk.core.http.ServiceCall;
import com.ibm.cloud.sdk.core.security.Authenticator;
import com.ibm.cloud.sdk.core.security.ConfigBasedAuthenticatorFactory;
import com.ibm.cloud.sdk.core.service.BaseService;
import com.ibm.cloud.sdk.core.util.RequestUtils;
import com.ibm.cloud.sdk.core.util.ResponseConverterUtils;
import com.ibm.watson.common.SdkCommon;
import com.ibm.watson.language_translator.v3.model.CreateModelOptions;
import com.ibm.watson.language_translator.v3.model.DeleteDocumentOptions;
import com.ibm.watson.language_translator.v3.model.DeleteModelOptions;
import com.ibm.watson.language_translator.v3.model.DeleteModelResult;
import com.ibm.watson.language_translator.v3.model.DocumentList;
import com.ibm.watson.language_translator.v3.model.DocumentStatus;
import com.ibm.watson.language_translator.v3.model.GetDocumentStatusOptions;
import com.ibm.watson.language_translator.v3.model.GetModelOptions;
import com.ibm.watson.language_translator.v3.model.GetTranslatedDocumentOptions;
import com.ibm.watson.language_translator.v3.model.IdentifiableLanguages;
import com.ibm.watson.language_translator.v3.model.IdentifiedLanguages;
import com.ibm.watson.language_translator.v3.model.IdentifyOptions;
import com.ibm.watson.language_translator.v3.model.Languages;
import com.ibm.watson.language_translator.v3.model.ListDocumentsOptions;
import com.ibm.watson.language_translator.v3.model.ListIdentifiableLanguagesOptions;
import com.ibm.watson.language_translator.v3.model.ListLanguagesOptions;
import com.ibm.watson.language_translator.v3.model.ListModelsOptions;
import com.ibm.watson.language_translator.v3.model.TranslateDocumentOptions;
import com.ibm.watson.language_translator.v3.model.TranslateOptions;
import com.ibm.watson.language_translator.v3.model.TranslationModel;
import com.ibm.watson.language_translator.v3.model.TranslationModels;
import com.ibm.watson.language_translator.v3.model.TranslationResult;
import java.io.InputStream;
import java.util.Map;
import java.util.Map.Entry;
import okhttp3.MultipartBody;

/**
 * IBM Watson&trade; Language Translator translates text from one language to another. The service
 * offers multiple IBM-provided translation models that you can customize based on your unique
 * terminology and language. Use Language Translator to take news from across the globe and present
 * it in your language, communicate with your customers in their own language, and more.
 *
 * @version v3
 * @see <a href="https://cloud.ibm.com/docs/language-translator/">Language Translator</a>
 */
public class LanguageTranslator extends BaseService {

  private static final String DEFAULT_SERVICE_NAME = "language_translator";

  private static final String DEFAULT_SERVICE_URL =
      "https://api.us-south.language-translator.watson.cloud.ibm.com";

  private String versionDate;

  /**
   * Constructs a new `LanguageTranslator` client using the DEFAULT_SERVICE_NAME.
   *
   * @param versionDate The version date (yyyy-MM-dd) of the REST API to use. Specifying this value
   *     will keep your API calls from failing when the service introduces breaking changes.
   */
  public LanguageTranslator(String versionDate) {
    this(
        versionDate,
        DEFAULT_SERVICE_NAME,
        ConfigBasedAuthenticatorFactory.getAuthenticator(DEFAULT_SERVICE_NAME));
  }

  /**
   * Constructs a new `LanguageTranslator` client with the DEFAULT_SERVICE_NAME and the specified
   * Authenticator.
   *
   * @param versionDate The version date (yyyy-MM-dd) of the REST API to use. Specifying this value
   *     will keep your API calls from failing when the service introduces breaking changes.
   * @param authenticator the Authenticator instance to be configured for this service
   */
  public LanguageTranslator(String versionDate, Authenticator authenticator) {
    this(versionDate, DEFAULT_SERVICE_NAME, authenticator);
  }

  /**
   * Constructs a new `LanguageTranslator` client with the specified serviceName.
   *
   * @param versionDate The version date (yyyy-MM-dd) of the REST API to use. Specifying this value
   *     will keep your API calls from failing when the service introduces breaking changes.
   * @param serviceName The name of the service to configure.
   */
  public LanguageTranslator(String versionDate, String serviceName) {
    this(versionDate, serviceName, ConfigBasedAuthenticatorFactory.getAuthenticator(serviceName));
  }

  /**
   * Constructs a new `LanguageTranslator` client with the specified Authenticator and serviceName.
   *
   * @param versionDate The version date (yyyy-MM-dd) of the REST API to use. Specifying this value
   *     will keep your API calls from failing when the service introduces breaking changes.
   * @param serviceName The name of the service to configure.
   * @param authenticator the Authenticator instance to be configured for this service
   */
  public LanguageTranslator(String versionDate, String serviceName, Authenticator authenticator) {
    super(serviceName, authenticator);
    setServiceUrl(DEFAULT_SERVICE_URL);
    com.ibm.cloud.sdk.core.util.Validator.isTrue(
        (versionDate != null) && !versionDate.isEmpty(), "version cannot be null.");
    this.versionDate = versionDate;
    this.configureService(serviceName);
  }

  /**
   * List supported languages.
   *
   * <p>Lists all supported languages. The method returns an array of supported languages with
   * information about each language. Languages are listed in alphabetical order by language code
   * (for example, `af`, `ar`).
   *
   * @param listLanguagesOptions the {@link ListLanguagesOptions} containing the options for the
   *     call
   * @return a {@link ServiceCall} with a response type of {@link Languages}
   */
  public ServiceCall<Languages> listLanguages(ListLanguagesOptions listLanguagesOptions) {
    String[] pathSegments = {"v3/languages"};
    RequestBuilder builder =
        RequestBuilder.get(RequestBuilder.constructHttpUrl(getServiceUrl(), pathSegments));
    builder.query("version", versionDate);
    Map<String, String> sdkHeaders =
        SdkCommon.getSdkHeaders("language_translator", "v3", "listLanguages");
    for (Entry<String, String> header : sdkHeaders.entrySet()) {
      builder.header(header.getKey(), header.getValue());
    }
    builder.header("Accept", "application/json");
    if (listLanguagesOptions != null) {}

    ResponseConverter<Languages> responseConverter =
        ResponseConverterUtils.getValue(
            new com.google.gson.reflect.TypeToken<Languages>() {}.getType());
    return createServiceCall(builder.build(), responseConverter);
  }

  /**
   * List supported languages.
   *
   * <p>Lists all supported languages. The method returns an array of supported languages with
   * information about each language. Languages are listed in alphabetical order by language code
   * (for example, `af`, `ar`).
   *
   * @return a {@link ServiceCall} with a response type of {@link Languages}
   */
  public ServiceCall<Languages> listLanguages() {
    return listLanguages(null);
  }

  /**
   * Translate.
   *
   * <p>Translates the input text from the source language to the target language. Specify a model
   * ID that indicates the source and target languages, or specify the source and target languages
   * individually. You can omit the source language to have the service attempt to detect the
   * language from the input text. If you omit the source language, the request must contain
   * sufficient input text for the service to identify the source language.
   *
   * @param translateOptions the {@link TranslateOptions} containing the options for the call
   * @return a {@link ServiceCall} with a response type of {@link TranslationResult}
   */
  public ServiceCall<TranslationResult> translate(TranslateOptions translateOptions) {
    com.ibm.cloud.sdk.core.util.Validator.notNull(
        translateOptions, "translateOptions cannot be null");
    String[] pathSegments = {"v3/translate"};
    RequestBuilder builder =
        RequestBuilder.post(RequestBuilder.constructHttpUrl(getServiceUrl(), pathSegments));
    builder.query("version", versionDate);
    Map<String, String> sdkHeaders =
        SdkCommon.getSdkHeaders("language_translator", "v3", "translate");
    for (Entry<String, String> header : sdkHeaders.entrySet()) {
      builder.header(header.getKey(), header.getValue());
    }
    builder.header("Accept", "application/json");
    final JsonObject contentJson = new JsonObject();
    contentJson.add(
        "text",
        com.ibm.cloud.sdk.core.util.GsonSingleton.getGson().toJsonTree(translateOptions.text()));
    if (translateOptions.modelId() != null) {
      contentJson.addProperty("model_id", translateOptions.modelId());
    }
    if (translateOptions.source() != null) {
      contentJson.addProperty("source", translateOptions.source());
    }
    if (translateOptions.target() != null) {
      contentJson.addProperty("target", translateOptions.target());
    }
    builder.bodyJson(contentJson);
    ResponseConverter<TranslationResult> responseConverter =
        ResponseConverterUtils.getValue(
            new com.google.gson.reflect.TypeToken<TranslationResult>() {}.getType());
    return createServiceCall(builder.build(), responseConverter);
  }

  /**
   * List identifiable languages.
   *
   * <p>Lists the languages that the service can identify. Returns the language code (for example,
   * `en` for English or `es` for Spanish) and name of each language.
   *
   * @param listIdentifiableLanguagesOptions the {@link ListIdentifiableLanguagesOptions} containing
   *     the options for the call
   * @return a {@link ServiceCall} with a response type of {@link IdentifiableLanguages}
   */
  public ServiceCall<IdentifiableLanguages> listIdentifiableLanguages(
      ListIdentifiableLanguagesOptions listIdentifiableLanguagesOptions) {
    String[] pathSegments = {"v3/identifiable_languages"};
    RequestBuilder builder =
        RequestBuilder.get(RequestBuilder.constructHttpUrl(getServiceUrl(), pathSegments));
    builder.query("version", versionDate);
    Map<String, String> sdkHeaders =
        SdkCommon.getSdkHeaders("language_translator", "v3", "listIdentifiableLanguages");
    for (Entry<String, String> header : sdkHeaders.entrySet()) {
      builder.header(header.getKey(), header.getValue());
    }
    builder.header("Accept", "application/json");
    if (listIdentifiableLanguagesOptions != null) {}

    ResponseConverter<IdentifiableLanguages> responseConverter =
        ResponseConverterUtils.getValue(
            new com.google.gson.reflect.TypeToken<IdentifiableLanguages>() {}.getType());
    return createServiceCall(builder.build(), responseConverter);
  }

  /**
   * List identifiable languages.
   *
   * <p>Lists the languages that the service can identify. Returns the language code (for example,
   * `en` for English or `es` for Spanish) and name of each language.
   *
   * @return a {@link ServiceCall} with a response type of {@link IdentifiableLanguages}
   */
  public ServiceCall<IdentifiableLanguages> listIdentifiableLanguages() {
    return listIdentifiableLanguages(null);
  }

  /**
   * Identify language.
   *
   * <p>Identifies the language of the input text.
   *
   * @param identifyOptions the {@link IdentifyOptions} containing the options for the call
   * @return a {@link ServiceCall} with a response type of {@link IdentifiedLanguages}
   */
  public ServiceCall<IdentifiedLanguages> identify(IdentifyOptions identifyOptions) {
    com.ibm.cloud.sdk.core.util.Validator.notNull(
        identifyOptions, "identifyOptions cannot be null");
    String[] pathSegments = {"v3/identify"};
    RequestBuilder builder =
        RequestBuilder.post(RequestBuilder.constructHttpUrl(getServiceUrl(), pathSegments));
    builder.query("version", versionDate);
    Map<String, String> sdkHeaders =
        SdkCommon.getSdkHeaders("language_translator", "v3", "identify");
    for (Entry<String, String> header : sdkHeaders.entrySet()) {
      builder.header(header.getKey(), header.getValue());
    }
    builder.header("Accept", "application/json");
    builder.bodyContent(identifyOptions.text(), "text/plain");
    ResponseConverter<IdentifiedLanguages> responseConverter =
        ResponseConverterUtils.getValue(
            new com.google.gson.reflect.TypeToken<IdentifiedLanguages>() {}.getType());
    return createServiceCall(builder.build(), responseConverter);
  }

  /**
   * List models.
   *
   * <p>Lists available translation models.
   *
   * @param listModelsOptions the {@link ListModelsOptions} containing the options for the call
   * @return a {@link ServiceCall} with a response type of {@link TranslationModels}
   */
  public ServiceCall<TranslationModels> listModels(ListModelsOptions listModelsOptions) {
    String[] pathSegments = {"v3/models"};
    RequestBuilder builder =
        RequestBuilder.get(RequestBuilder.constructHttpUrl(getServiceUrl(), pathSegments));
    builder.query("version", versionDate);
    Map<String, String> sdkHeaders =
        SdkCommon.getSdkHeaders("language_translator", "v3", "listModels");
    for (Entry<String, String> header : sdkHeaders.entrySet()) {
      builder.header(header.getKey(), header.getValue());
    }
    builder.header("Accept", "application/json");
    if (listModelsOptions != null) {
      if (listModelsOptions.source() != null) {
        builder.query("source", listModelsOptions.source());
      }
      if (listModelsOptions.target() != null) {
        builder.query("target", listModelsOptions.target());
      }
      if (listModelsOptions.xDefault() != null) {
        builder.query("default", String.valueOf(listModelsOptions.xDefault()));
      }
    }
    ResponseConverter<TranslationModels> responseConverter =
        ResponseConverterUtils.getValue(
            new com.google.gson.reflect.TypeToken<TranslationModels>() {}.getType());
    return createServiceCall(builder.build(), responseConverter);
  }

  /**
   * List models.
   *
   * <p>Lists available translation models.
   *
   * @return a {@link ServiceCall} with a response type of {@link TranslationModels}
   */
  public ServiceCall<TranslationModels> listModels() {
    return listModels(null);
  }

  /**
   * Create model.
   *
   * <p>Uploads training files to customize a translation model. You can customize a model with a
   * forced glossary or with a parallel corpus: * Use a *forced glossary* to force certain terms and
   * phrases to be translated in a specific way. You can upload only a single forced glossary file
   * for a model. The size of a forced glossary file for a custom model is limited to 10 MB. * Use a
   * *parallel corpus* when you want your custom model to learn from general translation patterns in
   * parallel sentences in your samples. What your model learns from a parallel corpus can improve
   * translation results for input text that the model has not been trained on. You can upload
   * multiple parallel corpora files with a request. To successfully train with parallel corpora,
   * the corpora files must contain a cumulative total of at least 5000 parallel sentences. The
   * cumulative size of all uploaded corpus files for a custom model is limited to 250 MB.
   *
   * <p>Depending on the type of customization and the size of the uploaded files, training time can
   * range from minutes for a glossary to several hours for a large parallel corpus. To create a
   * model that is customized with a parallel corpus and a forced glossary, customize the model with
   * a parallel corpus first and then customize the resulting model with a forced glossary.
   *
   * <p>You can create a maximum of 10 custom models per language pair. For more information about
   * customizing a translation model, including the formatting and character restrictions for data
   * files, see [Customizing your
   * model](https://cloud.ibm.com/docs/language-translator?topic=language-translator-customizing).
   *
   * <p>#### Supported file formats
   *
   * <p>You can provide your training data for customization in the following document formats: *
   * **TMX** (`.tmx`) - Translation Memory eXchange (TMX) is an XML specification for the exchange
   * of translation memories. * **XLIFF** (`.xliff`) - XML Localization Interchange File Format
   * (XLIFF) is an XML specification for the exchange of translation memories. * **CSV** (`.csv`) -
   * Comma-separated values (CSV) file with two columns for aligned sentences and phrases. The first
   * row contains the language code. * **TSV** (`.tsv` or `.tab`) - Tab-separated values (TSV) file
   * with two columns for aligned sentences and phrases. The first row contains the language code. *
   * **JSON** (`.json`) - Custom JSON format for specifying aligned sentences and phrases. *
   * **Microsoft Excel** (`.xls` or `.xlsx`) - Excel file with the first two columns for aligned
   * sentences and phrases. The first row contains the language code.
   *
   * <p>You must encode all text data in UTF-8 format. For more information, see [Supported document
   * formats for training
   * data](https://cloud.ibm.com/docs/language-translator?topic=language-translator-customizing#supported-document-formats-for-training-data).
   *
   * <p>#### Specifying file formats
   *
   * <p>You can indicate the format of a file by including the file extension with the file name.
   * Use the file extensions shown in **Supported file formats**.
   *
   * <p>Alternatively, you can omit the file extension and specify one of the following
   * `content-type` specifications for the file: * **TMX** - `application/x-tmx+xml` * **XLIFF** -
   * `application/xliff+xml` * **CSV** - `text/csv` * **TSV** - `text/tab-separated-values` *
   * **JSON** - `application/json` * **Microsoft Excel** -
   * `application/vnd.openxmlformats-officedocument.spreadsheetml.sheet`
   *
   * <p>For example, with `curl`, use the following `content-type` specification to indicate the
   * format of a CSV file named **glossary**:
   *
   * <p>`--form "forced_glossary=@glossary;type=text/csv"`.
   *
   * @param createModelOptions the {@link CreateModelOptions} containing the options for the call
   * @return a {@link ServiceCall} with a response type of {@link TranslationModel}
   */
  public ServiceCall<TranslationModel> createModel(CreateModelOptions createModelOptions) {
    com.ibm.cloud.sdk.core.util.Validator.notNull(
        createModelOptions, "createModelOptions cannot be null");
    com.ibm.cloud.sdk.core.util.Validator.isTrue(
        (createModelOptions.forcedGlossary() != null)
            || (createModelOptions.parallelCorpus() != null),
        "At least one of forcedGlossary or parallelCorpus must be supplied.");
    String[] pathSegments = {"v3/models"};
    RequestBuilder builder =
        RequestBuilder.post(RequestBuilder.constructHttpUrl(getServiceUrl(), pathSegments));
    builder.query("version", versionDate);
    Map<String, String> sdkHeaders =
        SdkCommon.getSdkHeaders("language_translator", "v3", "createModel");
    for (Entry<String, String> header : sdkHeaders.entrySet()) {
      builder.header(header.getKey(), header.getValue());
    }
    builder.header("Accept", "application/json");
    builder.query("base_model_id", createModelOptions.baseModelId());
    if (createModelOptions.name() != null) {
      builder.query("name", createModelOptions.name());
    }
    MultipartBody.Builder multipartBuilder = new MultipartBody.Builder();
    multipartBuilder.setType(MultipartBody.FORM);
    if (createModelOptions.forcedGlossary() != null) {
      okhttp3.RequestBody forcedGlossaryBody =
          RequestUtils.inputStreamBody(
              createModelOptions.forcedGlossary(), "application/octet-stream");
      multipartBuilder.addFormDataPart("forced_glossary", "filename", forcedGlossaryBody);
    }
    if (createModelOptions.parallelCorpus() != null) {
      okhttp3.RequestBody parallelCorpusBody =
          RequestUtils.inputStreamBody(
              createModelOptions.parallelCorpus(), "application/octet-stream");
      multipartBuilder.addFormDataPart("parallel_corpus", "filename", parallelCorpusBody);
    }
    builder.body(multipartBuilder.build());
    ResponseConverter<TranslationModel> responseConverter =
        ResponseConverterUtils.getValue(
            new com.google.gson.reflect.TypeToken<TranslationModel>() {}.getType());
    return createServiceCall(builder.build(), responseConverter);
  }

  /**
   * Delete model.
   *
   * <p>Deletes a custom translation model.
   *
   * @param deleteModelOptions the {@link DeleteModelOptions} containing the options for the call
   * @return a {@link ServiceCall} with a response type of {@link DeleteModelResult}
   */
  public ServiceCall<DeleteModelResult> deleteModel(DeleteModelOptions deleteModelOptions) {
    com.ibm.cloud.sdk.core.util.Validator.notNull(
        deleteModelOptions, "deleteModelOptions cannot be null");
    String[] pathSegments = {"v3/models"};
    String[] pathParameters = {deleteModelOptions.modelId()};
    RequestBuilder builder =
        RequestBuilder.delete(
            RequestBuilder.constructHttpUrl(getServiceUrl(), pathSegments, pathParameters));
    builder.query("version", versionDate);
    Map<String, String> sdkHeaders =
        SdkCommon.getSdkHeaders("language_translator", "v3", "deleteModel");
    for (Entry<String, String> header : sdkHeaders.entrySet()) {
      builder.header(header.getKey(), header.getValue());
    }
    builder.header("Accept", "application/json");

    ResponseConverter<DeleteModelResult> responseConverter =
        ResponseConverterUtils.getValue(
            new com.google.gson.reflect.TypeToken<DeleteModelResult>() {}.getType());
    return createServiceCall(builder.build(), responseConverter);
  }

  /**
   * Get model details.
   *
   * <p>Gets information about a translation model, including training status for custom models. Use
   * this API call to poll the status of your customization request. A successfully completed
   * training has a status of `available`.
   *
   * @param getModelOptions the {@link GetModelOptions} containing the options for the call
   * @return a {@link ServiceCall} with a response type of {@link TranslationModel}
   */
  public ServiceCall<TranslationModel> getModel(GetModelOptions getModelOptions) {
    com.ibm.cloud.sdk.core.util.Validator.notNull(
        getModelOptions, "getModelOptions cannot be null");
    String[] pathSegments = {"v3/models"};
    String[] pathParameters = {getModelOptions.modelId()};
    RequestBuilder builder =
        RequestBuilder.get(
            RequestBuilder.constructHttpUrl(getServiceUrl(), pathSegments, pathParameters));
    builder.query("version", versionDate);
    Map<String, String> sdkHeaders =
        SdkCommon.getSdkHeaders("language_translator", "v3", "getModel");
    for (Entry<String, String> header : sdkHeaders.entrySet()) {
      builder.header(header.getKey(), header.getValue());
    }
    builder.header("Accept", "application/json");

    ResponseConverter<TranslationModel> responseConverter =
        ResponseConverterUtils.getValue(
            new com.google.gson.reflect.TypeToken<TranslationModel>() {}.getType());
    return createServiceCall(builder.build(), responseConverter);
  }

  /**
   * List documents.
   *
   * <p>Lists documents that have been submitted for translation.
   *
   * @param listDocumentsOptions the {@link ListDocumentsOptions} containing the options for the
   *     call
   * @return a {@link ServiceCall} with a response type of {@link DocumentList}
   */
  public ServiceCall<DocumentList> listDocuments(ListDocumentsOptions listDocumentsOptions) {
    String[] pathSegments = {"v3/documents"};
    RequestBuilder builder =
        RequestBuilder.get(RequestBuilder.constructHttpUrl(getServiceUrl(), pathSegments));
    builder.query("version", versionDate);
    Map<String, String> sdkHeaders =
        SdkCommon.getSdkHeaders("language_translator", "v3", "listDocuments");
    for (Entry<String, String> header : sdkHeaders.entrySet()) {
      builder.header(header.getKey(), header.getValue());
    }
    builder.header("Accept", "application/json");
    if (listDocumentsOptions != null) {}

    ResponseConverter<DocumentList> responseConverter =
        ResponseConverterUtils.getValue(
            new com.google.gson.reflect.TypeToken<DocumentList>() {}.getType());
    return createServiceCall(builder.build(), responseConverter);
  }

  /**
   * List documents.
   *
   * <p>Lists documents that have been submitted for translation.
   *
   * @return a {@link ServiceCall} with a response type of {@link DocumentList}
   */
  public ServiceCall<DocumentList> listDocuments() {
    return listDocuments(null);
  }

  /**
   * Translate document.
   *
   * <p>Submit a document for translation. You can submit the document contents in the `file`
   * parameter, or you can reference a previously submitted document by document ID.
   *
   * @param translateDocumentOptions the {@link TranslateDocumentOptions} containing the options for
   *     the call
   * @return a {@link ServiceCall} with a response type of {@link DocumentStatus}
   */
  public ServiceCall<DocumentStatus> translateDocument(
      TranslateDocumentOptions translateDocumentOptions) {
    com.ibm.cloud.sdk.core.util.Validator.notNull(
        translateDocumentOptions, "translateDocumentOptions cannot be null");
    String[] pathSegments = {"v3/documents"};
    RequestBuilder builder =
        RequestBuilder.post(RequestBuilder.constructHttpUrl(getServiceUrl(), pathSegments));
    builder.query("version", versionDate);
    Map<String, String> sdkHeaders =
        SdkCommon.getSdkHeaders("language_translator", "v3", "translateDocument");
    for (Entry<String, String> header : sdkHeaders.entrySet()) {
      builder.header(header.getKey(), header.getValue());
    }
    builder.header("Accept", "application/json");
    MultipartBody.Builder multipartBuilder = new MultipartBody.Builder();
    multipartBuilder.setType(MultipartBody.FORM);
    okhttp3.RequestBody fileBody =
        RequestUtils.inputStreamBody(
            translateDocumentOptions.file(), translateDocumentOptions.fileContentType());
    multipartBuilder.addFormDataPart("file", translateDocumentOptions.filename(), fileBody);
    if (translateDocumentOptions.modelId() != null) {
      multipartBuilder.addFormDataPart("model_id", translateDocumentOptions.modelId());
    }
    if (translateDocumentOptions.source() != null) {
      multipartBuilder.addFormDataPart("source", translateDocumentOptions.source());
    }
    if (translateDocumentOptions.target() != null) {
      multipartBuilder.addFormDataPart("target", translateDocumentOptions.target());
    }
    if (translateDocumentOptions.documentId() != null) {
      multipartBuilder.addFormDataPart("document_id", translateDocumentOptions.documentId());
    }
    builder.body(multipartBuilder.build());
    ResponseConverter<DocumentStatus> responseConverter =
        ResponseConverterUtils.getValue(
            new com.google.gson.reflect.TypeToken<DocumentStatus>() {}.getType());
    return createServiceCall(builder.build(), responseConverter);
  }

  /**
   * Get document status.
   *
   * <p>Gets the translation status of a document.
   *
   * @param getDocumentStatusOptions the {@link GetDocumentStatusOptions} containing the options for
   *     the call
   * @return a {@link ServiceCall} with a response type of {@link DocumentStatus}
   */
  public ServiceCall<DocumentStatus> getDocumentStatus(
      GetDocumentStatusOptions getDocumentStatusOptions) {
    com.ibm.cloud.sdk.core.util.Validator.notNull(
        getDocumentStatusOptions, "getDocumentStatusOptions cannot be null");
    String[] pathSegments = {"v3/documents"};
    String[] pathParameters = {getDocumentStatusOptions.documentId()};
    RequestBuilder builder =
        RequestBuilder.get(
            RequestBuilder.constructHttpUrl(getServiceUrl(), pathSegments, pathParameters));
    builder.query("version", versionDate);
    Map<String, String> sdkHeaders =
        SdkCommon.getSdkHeaders("language_translator", "v3", "getDocumentStatus");
    for (Entry<String, String> header : sdkHeaders.entrySet()) {
      builder.header(header.getKey(), header.getValue());
    }
    builder.header("Accept", "application/json");

    ResponseConverter<DocumentStatus> responseConverter =
        ResponseConverterUtils.getValue(
            new com.google.gson.reflect.TypeToken<DocumentStatus>() {}.getType());
    return createServiceCall(builder.build(), responseConverter);
  }

  /**
   * Delete document.
   *
   * <p>Deletes a document.
   *
   * @param deleteDocumentOptions the {@link DeleteDocumentOptions} containing the options for the
   *     call
   * @return a {@link ServiceCall} with a response type of Void
   */
  public ServiceCall<Void> deleteDocument(DeleteDocumentOptions deleteDocumentOptions) {
    com.ibm.cloud.sdk.core.util.Validator.notNull(
        deleteDocumentOptions, "deleteDocumentOptions cannot be null");
    String[] pathSegments = {"v3/documents"};
    String[] pathParameters = {deleteDocumentOptions.documentId()};
    RequestBuilder builder =
        RequestBuilder.delete(
            RequestBuilder.constructHttpUrl(getServiceUrl(), pathSegments, pathParameters));
    builder.query("version", versionDate);
    Map<String, String> sdkHeaders =
        SdkCommon.getSdkHeaders("language_translator", "v3", "deleteDocument");
    for (Entry<String, String> header : sdkHeaders.entrySet()) {
      builder.header(header.getKey(), header.getValue());
    }

    ResponseConverter<Void> responseConverter = ResponseConverterUtils.getVoid();
    return createServiceCall(builder.build(), responseConverter);
  }

  /**
   * Get translated document.
   *
   * <p>Gets the translated document associated with the given document ID.
   *
   * @param getTranslatedDocumentOptions the {@link GetTranslatedDocumentOptions} containing the
   *     options for the call
   * @return a {@link ServiceCall} with a response type of {@link InputStream}
   */
  public ServiceCall<InputStream> getTranslatedDocument(
      GetTranslatedDocumentOptions getTranslatedDocumentOptions) {
    com.ibm.cloud.sdk.core.util.Validator.notNull(
        getTranslatedDocumentOptions, "getTranslatedDocumentOptions cannot be null");
    String[] pathSegments = {"v3/documents", "translated_document"};
    String[] pathParameters = {getTranslatedDocumentOptions.documentId()};
    RequestBuilder builder =
        RequestBuilder.get(
            RequestBuilder.constructHttpUrl(getServiceUrl(), pathSegments, pathParameters));
    builder.query("version", versionDate);
    Map<String, String> sdkHeaders =
        SdkCommon.getSdkHeaders("language_translator", "v3", "getTranslatedDocument");
    for (Entry<String, String> header : sdkHeaders.entrySet()) {
      builder.header(header.getKey(), header.getValue());
    }
    if (getTranslatedDocumentOptions.accept() != null) {
      builder.header("Accept", getTranslatedDocumentOptions.accept());
    }
    ResponseConverter<InputStream> responseConverter = ResponseConverterUtils.getInputStream();
    return createServiceCall(builder.build(), responseConverter);
  }
}
