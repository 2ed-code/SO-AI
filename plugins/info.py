from plugins.base import Plugin

class InfoPlugin(Plugin):

    def can_handle(self, text):
        text = text.lower()
        return "اسمك" in text or "ازيك" in text

    def handle(self, text):
        text = text.lower()

        if "اسمك" in text:
            return "أنا SO AI"

        if "ازيك" in text:
            return "أنا بخير"

        return None
