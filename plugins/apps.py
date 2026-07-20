from plugins.base import Plugin
import subprocess

class AppsPlugin(Plugin):

    APPS = {
        "واتساب": "com.whatsapp",
        "تليجرام": "org.telegram.messenger",
        "تيليجرام": "org.telegram.messenger",
        "يوتيوب": "com.google.android.youtube",
        "كروم": "com.android.chrome",
        "الإعدادات": "com.android.settings"
    }

    def can_handle(self, text):
        text = text.strip()
        return text.startswith("افتح") or text.startswith("فتح")

    def handle(self, text):
        for app, package in self.APPS.items():
            if app in text:
                subprocess.run([
                    "monkey",
                    "-p",
                    package,
                    "-c",
                    "android.intent.category.LAUNCHER",
                    "1"
                ])
                return f"تم فتح {app}"

        return "لم أجد التطبيق."
