package io.botlify.jast.objects;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.jetbrains.annotations.NotNull;

/**
 * This class represents a content type in a HTTP request.
 */
@EqualsAndHashCode
@ToString
public class ContentType {

    public static final ContentType APPLICATION_JSON = new ContentType("application/json");
    public static final ContentType APPLICATION_XML = new ContentType("application/xml");
    public static final ContentType TEXT_PLAIN = new ContentType("text/plain");
    public static final ContentType TEXT_HTML = new ContentType("text/html");
    public static final ContentType TEXT_XML = new ContentType("text/xml");
    public static final ContentType MULTIPART_FORM_DATA = new ContentType("multipart/form-data");
    public static final ContentType APPLICATION_FORM_URLENCODED = new ContentType("application/x-www-form-urlencoded");
    public static final ContentType APPLICATION_OCTET_STREAM = new ContentType("application/octet-stream");
    public static final ContentType APPLICATION_XHTML_XML = new ContentType("application/xhtml+xml");
    public static final ContentType APPLICATION_X_WWW_FORM_URLENCODED = new ContentType("application/x-www-form-urlencoded");
    public static final ContentType IMAGE_GIF = new ContentType("image/gif");
    public static final ContentType IMAGE_JPEG = new ContentType("image/jpeg");
    public static final ContentType IMAGE_PNG = new ContentType("image/png");
    public static final ContentType IMAGE_SVG_XML = new ContentType("image/svg+xml");
    public static final ContentType IMAGE_TIFF = new ContentType("image/tiff");
    public static final ContentType IMAGE_WEBP = new ContentType("image/webp");

    /**
     * The wildcard content type.
     * This content type will match any content type.
     */
    public static final ContentType WILDCARD = new ContentType("*/*");

    /**
     * The type of the content.
     */
    @Getter @NotNull
    private final String type;

    public ContentType(@NotNull final String type) {
        this.type = type;
    }

}