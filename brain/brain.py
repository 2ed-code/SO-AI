from plugins.info import InfoPlugin
from plugins.apps import AppsPlugin

class Brain:

    def __init__(self):
        self.plugins = [
            AppsPlugin(),
            InfoPlugin()
        ]

    def think(self, text):
        for plugin in self.plugins:
            if plugin.can_handle(text):
                return plugin.handle(text)

        return "لا أعرف هذا الأمر."
