document.addEventListener("DOMContentLoaded", function () {
  themeChange();
});

const nonsecureCopyToClipboard = (target, text) => {
  const textArea = document.createElement("textarea");
  textArea.value = text;
  target.prepend(textArea);
  textArea.focus();
  textArea.select();
  try {
    document.execCommand('copy')
  } catch (err) {
  }
  target.removeChild(textArea);
};

function copyToClipboard(target, text) {
  if (window.isSecureContext && navigator.clipboard) {
    navigator.clipboard.writeText(text);
  } else {
    nonsecureCopyToClipboard(target, text);
  }
}