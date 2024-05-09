class Latch:
    def __init__(self):
        self.data = None

    def set_data(self, value):
        self.data = value

    def get_data(self):
        return self.data

    def clear(self):
        self.data = None
