package ysaak.garde.service.mapping;

/**
 * Exception announcing that the conversion is not supported
 */
public class UnsupportedConversionException extends ConversionException {
  public UnsupportedConversionException(Class<?> fromClass, Class<?> toClass) {
    super("Unsupported conversion from " + fromClass.toGenericString() + " to " + toClass.toGenericString());
  }
}
