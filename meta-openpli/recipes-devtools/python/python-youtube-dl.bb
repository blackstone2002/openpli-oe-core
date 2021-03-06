SUMMARY = "Download videos from YouTube (and more sites)"
DESCRIPTION = "youtube-dl is a small command-line program to download videos \
from YouTube.com and a few more sites. It requires the Python interpreter \
(2.6, 2.7, or 3.2+), and it is not platform specific"
HOMEPAGE = "http://rg3.github.io/youtube-dl/"
SECTION = "devel/python"
LICENSE = "LGPLv2.1"
LIC_FILES_CHKSUM = "file://LICENSE;md5=9936da26f04f6454c738c5f4fda24799"

DEPENDS = "libxml2 bash-completion"

inherit gitpkgv

SRCREV = "${AUTOREV}"
PE = "1"
PV = "git${SRCPV}"
PKGV = "${GITPKGVTAG}"
SRC_URI = "git://source.netsyms.com/Mirrors/l1ving_youtube-dl.git;protocol=https;branch=master"

S = "${WORKDIR}/git"

inherit setuptools

EXTRA_OEMAKE = "PYTHON=${PYTHON}"

do_compile_prepend() {
    oe_runmake lazy-extractors youtube-dl.bash-completion
}

do_install_append() {
    mv ${D}${datadir}/etc ${D}${sysconfdir}
    install -m 0755 -d ${D}${sysconfdir}/bash_completion.d
    install -m 0644 youtube-dl.bash-completion ${D}${sysconfdir}/bash_completion.d
}

RDEPENDS_${PN} = " \
    python-email \
    python-subprocess \
    python-unixadmin \
    python-ctypes \
    python-argparse \
    python-html \
    "

RDEPENDS_{PN}-src = "${PN}"
FILES_${PN}-src = " \
    ${libdir}/${PYTHON_DIR}/site-packages/*/*.py \
    ${libdir}/${PYTHON_DIR}/site-packages/*/*/*.py \
    ${libdir}/${PYTHON_DIR}/site-packages/*/*/*/*.py \
    ${libdir}/${PYTHON_DIR}/site-packages/*/*/*/*/*.py \
    ${datadir}/etc/* \
    "

FILES_${PN} += "${sysconfdir}"
