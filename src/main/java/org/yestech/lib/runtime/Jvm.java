/*
 * Copyright LGPL3
 * YES Technology Association
 * http://yestech.org
 *
 * http://www.opensource.org/licenses/lgpl-3.0.html
 */

/*
 *
 * Original Author:  Artie Copeland
 * Last Modified Date: $DateTime: $
 */
package org.yestech.lib.runtime;

import java.lang.reflect.Method;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author $Author: $
 * @version $Revision: $
 */
final public class Jvm {
    /**
     * Version info is parsed from system properties and stored here.
     */
    public static final JvmVersion VERSION;

    static {
        VERSION = new JvmVersion(System.getProperties());
    }

    private Jvm() {
    }

    /**
     * Get mega version (ie 1 in 1.2.3)
     *
     * @return Mega version
     */
    public static int getMegaVersion() {
        return VERSION.getMegaVersion();
    }

    /**
     * Get major version (ie 2 in 1.2.3)
     *
     * @return Major version
     */
    public static int getMajorVersion() {
        return VERSION.getMajorVersion();
    }

    /**
     * Get minor version (ie 3 in 1.2.3)
     *
     * @return Minor version
     */
    public static int getMinorVersion() {
        return VERSION.getMinorVersion();
    }

    /**
     * Get patch level (ie 12 in 1.4.2_12)
     *
     * @return Patch level
     */
    public static String getPatchLevel() {
        return VERSION.getPatchLevel();
    }

    /**
     * True if mega/major is 1.4
     *
     * @return True if 1.4
     */
    public static boolean isJDK14() {
        return VERSION.isJDK14();
    }

    /**
     * True if mega/major is 1.5
     *
     * @return True if 1.5
     */
    public static boolean isJDK15() {
        return VERSION.isJDK15();
    }

    /**
     * True if mega/major is 1.6
     *
     * @return True if 1.6
     */
    public static boolean isJDK16() {
        return VERSION.isJDK16();
    }

    /**
     * True if mega/major is 1.7
     *
     * @return True if 1.7
     */
    public static boolean isJDK17() {
        return VERSION.isJDK17();
    }

    /**
     * True if JDK is 1.5+
     *
     * @return True if JDK 1.5/1.6/1.7
     */
    public static boolean isJDK15Compliant() {
        return VERSION.getMajorVersion() >= 5;
    }

    /**
     * True if JDK is 1.6+
     *
     * @return True if JDK 1.6/1.7
     */
    public static boolean isJDK16Compliant() {
        return VERSION.getMajorVersion() >= 6;
    }

    /**
     * True if IBM JDK
     *
     * @return True if IBM JDK
     */
    public static boolean isIBM() {
        if (VERSION == null) {
            // Our instrumentation for java.lang.reflect.Field can end up calling here while in <clinit> for
            // this class -- this avoids the NPE
            return JvmVersion.thisVMisIBM();
        }
        return VERSION.isIBM();
    }

    public static void assertIsIbm() {
        if (!isIBM()) {
            throw new AssertionError("not ibm");
        }
    }

    /**
     * True if JRockit
     *
     * @return True if BEA Jrockit VM
     */
    public static boolean isJRockit() {
        return VERSION.isJRockit();
    }

    /**
     * True if Azul
     *
     * @return True if Azul VM
     */
    public static boolean isAzul() {
        return VERSION.isAzul();
    }

    private static class JvmVersion {
        private static final Pattern JVM_VERSION_PATTERN = Pattern
                .compile("^(\\p{Digit})\\.(\\p{Digit})\\.(\\p{Digit})(?:[-_](.+))?$");
        private static final Pattern IBM_SERVICE_RELEASE_PATTERN = Pattern
                .compile("^[^-]+-\\p{Digit}{8}[^\\p{Space}]*\\p{Space}*\\(.*(SR\\p{Digit}+).*\\)$");

        private final String vmVersion;
        private final int mega;
        private final int major;
        private final int minor;
        private final String patch;
        private final boolean isIBM;
        private final boolean isJRockit;
        private final boolean isAzul;

        /**
         * Construct with system properties, which will be parsed to determine version. Looks at properties like java.version,
         * java.runtime.version, jrockit.version, java.vm.name, and java.vendor.
         *
         * @param props Typically System.getProperties()
         */
        public JvmVersion(final Properties props) {
            this(javaVersion(props), runtimeVersion(props), isJRockit(props), isIBM(props), isAzul(props));
        }

        /**
         * Construct with specific version information
         *
         * @param vendorVersion  Version pattern like 1.4.2_12
         * @param runtimeVersion Runtime version pattern like 1.4.2_12-269
         * @param isJRockit      True if BEA JRockit JVM
         * @param isIBM          True if IBM JVM
         */
        private JvmVersion(final String vendorVersion, final String runtimeVersion, final boolean isJRockit,
                          final boolean isIBM, final boolean isAzul) {
            this.isIBM = isIBM;
            this.isJRockit = isJRockit;
            this.isAzul = isAzul;
            final Matcher versionMatcher = JVM_VERSION_PATTERN.matcher(vendorVersion);
            if (versionMatcher.matches()) {
                mega = Integer.parseInt(versionMatcher.group(1));
                major = Integer.parseInt(versionMatcher.group(2));
                minor = Integer.parseInt(versionMatcher.group(3));
                String version_patch = versionMatcher.groupCount() == 4 ? versionMatcher.group(4) : null;
                if (isIBM) {
                    final Matcher serviceReleaseMatcher = IBM_SERVICE_RELEASE_PATTERN.matcher(runtimeVersion);
                    if (serviceReleaseMatcher.matches()) {
                        String serviceRelease = serviceReleaseMatcher.groupCount() == 1 ? serviceReleaseMatcher.group(1)
                                .toLowerCase() : null;
                        if (null == version_patch && null == serviceRelease) {
                            patch = null;
                        } else if (null == version_patch) {
                            patch = serviceRelease;
                        } else if (null == serviceRelease) {
                            patch = version_patch;
                        } else {
                            patch = version_patch + serviceRelease;
                        }
                    } else {
                        throw new RuntimeException(vendorVersion + " : " + runtimeVersion);
                    }
                } else {
                    patch = version_patch;
                }
            } else {
                throw new RuntimeException(vendorVersion);
            }
            this.vmVersion = this.mega + "." + this.major + "." + this.minor + (null == patch ? "" : "_" + patch);
        }

        /**
         * Given the history of SunOS and Java version numbering by Sun, this will return '1' for a long time to come. Mega
         * version = 1 in 1.2.3
         *
         * @return Mega version
         */
        public int getMegaVersion() {
            return mega;
        }

        /**
         * Get major version (ie 2 in 1.2.3)
         *
         * @return Major version
         */
        public int getMajorVersion() {
            return major;
        }

        /**
         * Get minor version (ie 3 in 1.2.3)
         *
         * @return Minor version
         */
        public int getMinorVersion() {
            return minor;
        }

        /**
         * Get patch level (ie 12 in 1.2.3_12)
         *
         * @return Patch level
         */
        public String getPatchLevel() {
            return patch;
        }

        /**
         * @return True if JDK 1.4
         */
        public boolean isJDK14() {
            return mega == 1 && major == 4;
        }

        /**
         * @return True if JDK 1.5
         */
        public boolean isJDK15() {
            return mega == 1 && major == 5;
        }

        /**
         * @return True if JDK 1.6
         */
        public boolean isJDK16() {
            return mega == 1 && major == 6;
        }

        /**
         * @return True if JDK 1.7
         */
        public boolean isJDK17() {
            return mega == 1 && major == 7;
        }

        /**
         * @return True if IBM JVM
         */
        public boolean isIBM() {
            return isIBM;
        }

        /**
         * @return True if Azul VM
         */
        public boolean isAzul() {
            return isAzul;
        }

        /**
         * @return True if BEA JRockit
         */
        public boolean isJRockit() {
            return isJRockit;
        }

        /**
         * @param o Other version
         * @return True if other version has identical version string
         */
        public boolean equals(final Object o) {
            if (!(o instanceof JvmVersion)) {
                return false;
            }

            final JvmVersion other = (JvmVersion) o;
            return vmVersion.equals(other.vmVersion);
        }

        public int hashCode() {
            return vmVersion.hashCode();
        }

        public String toString() {
            return vmVersion;
        }

        private static String javaVersion(Properties props) {
            return props.getProperty("java.version", "<error: java.version not specified in properties>");
        }

        private static String runtimeVersion(Properties props) {
            if (thisVMisIBM()) {
                // It's not safe to read "java.runtime.version" from system properties until a certain point in startup
                // Specifically there is a race to set this prop in com.ibm.misc.SystemIntialization.lastChanceHook() and the
                // start of the management agent thread there (MNK-393)
                return getIBMRuntimeVersion();
            } else {
                return props.getProperty("java.runtime.version", "<error: java.runtime.version not specified in properties>");
            }
        }

        static boolean thisVMisIBM() {
            return isIBM(System.getProperties());
        }

        private static String getIBMRuntimeVersion() {
            try {
                Class c = Class.forName("com.ibm.misc.JavaRuntimeVersion");
                Method m = c.getDeclaredMethod("getValue", new Class[]{});
                m.setAccessible(true);
                return (String) m.invoke(c, new Object[]{});
            } catch (Throwable t) {
                throw new RuntimeException(t);
            }
        }

        private static boolean isAzul(Properties props) {
            return props.getProperty("java.vendor", "").toLowerCase().indexOf("azul") >= 0;
        }

        private static boolean isIBM(Properties props) {
            return props.getProperty("java.vendor", "").toLowerCase().startsWith("ibm ");
        }

        private static boolean isJRockit(Properties props) {
            return props.getProperty("jrockit.version") != null
                    || props.getProperty("java.vm.name", "").toLowerCase().indexOf("jrockit") >= 0;
        }
    }

}
