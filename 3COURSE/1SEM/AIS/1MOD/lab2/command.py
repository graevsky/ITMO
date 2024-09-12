from dataclasses import dataclass


@dataclass
class Command:
    format: str
    description: str
    query: str
