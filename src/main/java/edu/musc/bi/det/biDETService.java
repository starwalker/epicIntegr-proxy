package edu.musc.bi.det;

import edu.musc.bi.det.model.modelPatRegRecord;
import edu.musc.bi.det.moms.impactt.modelLandingPageIntakeRecord;
// import io.smallrye.mutiny.Uni;

import io.smallrye.common.annotation.NonBlocking;
import io.smallrye.mutiny.Uni;
//import java.util.concurrent.CompletionStage;
import java.util.concurrent.CompletionStage;
import org.eclipse.microprofile.rest.client.annotation.RegisterClientHeaders;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

//import java.util.Set;
import java.util.Set;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.FormParam;
// import io.quarkus.rest.client.reactive.ClientExceptionMapper;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
//import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MediaType;
import javax.enterprise.context.ApplicationScoped;

/**
 * A version of {@link RestInterface} that doesn't have {@link
 * org.eclipse.microprofile.rest.client.inject.RegisterRestClient} and can be used only
 * programmatically, i.e. with the builder.
 */
@RegisterRestClient(configKey = "redcap-api")
@RegisterClientHeaders
@ApplicationScoped
public interface biDETService {
    /*
    //@MultipartForm ModelDETFormDto formData
    @DefaultValue("token") @FormParam("token") String token,
    @DefaultValue("record") @FormParam("context") String content,
    @DefaultValue("json") @FormParam("format") String format,
    @DefaultValue("flat") @FormParam("type") String type,
    @DefaultValue("[3]") @FormParam("records") String records,
    @FormParam("fields") String fields,
    @FormParam("forms") String forms,
    @FormParam("events") String events,
    @DefaultValue("label") @FormParam("rawOrLabel") String rawOrLabel,
    @DefaultValue("label") @FormParam("rawOrLabelHeaders") String rawOrLabelHeaders,
    @DefaultValue("true") @FormParam("exportCheckboxLabel") String exportCheckboxLabel,
    @DefaultValue("true") @FormParam("exportSurveyFields") String exportSurveyFields,
    @DefaultValue("true") @FormParam("exportDataAccessGroups") String exportDataAccessGroups,
    @DefaultValue("json") @FormParam("returnFormat") String returnFormat
    */

    @POST
    @Path("api/")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    //@Consumes("application/x-www-form-urlencoded")
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<Set<modelLandingPageIntakeRecord>> exportLandingRec(
            @FormParam("token") String token,
            @FormParam("content") String content,
            @FormParam("action") String action,
            @FormParam("format") String format,
            @FormParam("type") String type,
            @DefaultValue("") @FormParam("csvDelimiter") String csvDelimiter,
            @FormParam("records") String records,
            @FormParam("fields") String fields,
            @FormParam("events") String events,
            @DefaultValue("label") @FormParam("rawOrLabel") String rawOrLabel,
            @DefaultValue("label") @FormParam("rawOrLabelHeaders") String rawOrLabelHeaders,
            @DefaultValue("true") @FormParam("exportCheckboxLabel") String exportCheckboxLabel,
            @DefaultValue("true") @FormParam("exportSurveyFields") String exportSurveyFields,
            @DefaultValue("false") @FormParam("exportDataAccessGroups")
                    String exportDataAccessGroups,
            @DefaultValue("json") @FormParam("returnFormat") String returnFormat);

    @POST
    @Path("api/")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    Set<modelPatRegRecord> exportPatregRec(
            @FormParam("token") String token,
            @FormParam("content") String content,
            @FormParam("action") String action,
            @FormParam("format") String format,
            @FormParam("type") String type,
            @DefaultValue("") @FormParam("csvDelimiter") String csvDelimiter,
            @FormParam("records") String records,
            @FormParam("fields") String fields,
            @FormParam("events") String events,
            @DefaultValue("label") @FormParam("rawOrLabel") String rawOrLabel,
            @DefaultValue("label") @FormParam("rawOrLabelHeaders") String rawOrLabelHeaders,
            @DefaultValue("true") @FormParam("exportCheckboxLabel") String exportCheckboxLabel,
            @DefaultValue("true") @FormParam("exportSurveyFields") String exportSurveyFields,
            @DefaultValue("false") @FormParam("exportDataAccessGroups")
                    String exportDataAccessGroups,
            @DefaultValue("json") @FormParam("returnFormat") String returnFormat);

    @POST
    @Path("api/")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    Uni<List<modelPatRegRecord>> exportPatregRec_Async(
            @FormParam("token") String token,
            @FormParam("content") String content,
            @FormParam("action") String action,
            @FormParam("format") String format,
            @FormParam("type") String type,
            @DefaultValue("") @FormParam("csvDelimiter") String csvDelimiter,
            @FormParam("records") String records,
            @FormParam("fields") String fields,
            @FormParam("events") String events,
            @DefaultValue("label") @FormParam("rawOrLabel") String rawOrLabel,
            @DefaultValue("label") @FormParam("rawOrLabelHeaders") String rawOrLabelHeaders,
            @DefaultValue("true") @FormParam("exportCheckboxLabel") String exportCheckboxLabel,
            @DefaultValue("true") @FormParam("exportSurveyFields") String exportSurveyFields,
            @DefaultValue("false") @FormParam("exportDataAccessGroups")
                    String exportDataAccessGroups,
            @DefaultValue("json") @FormParam("returnFormat") String returnFormat);

    /*
    @ClientExceptionMapper
    static RuntimeException toException(Response response) {
        if (response.getStatus() == 500) {
            return new RuntimeException("The remote service responded with HTTP 500");
        }
        return null;
    }
    */
    @POST
    @Path("api/")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    Uni<String> patRegFeedback2Redcap(
            @FormParam("token") String token,
            @FormParam("content") String content,
            @FormParam("format") String format,
            @FormParam("type") String type,
            @DefaultValue("normal") @FormParam("overwriteBehavior") String behavior,
            @FormParam("data") String data);
}
