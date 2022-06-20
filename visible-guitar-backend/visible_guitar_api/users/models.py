from django.contrib.auth.base_user import BaseUserManager, AbstractBaseUser
from django.contrib.auth.models import PermissionsMixin
from django.core import validators
from django.db import models

from visible_guitar.models import Chord, Melody


class MusicianUserManager(BaseUserManager):

    def __create_user(self, username, email, password=None, **fields):
        if not username:
            raise ValueError("Username must be set")
        if not email:
            raise ValueError("Email must be set")

        email = self.normalize_email(email)
        user = self.model(
            username=username,
            email=email,
            **fields
        )
        user.set_password(password)
        user.save(using=self._db)
        return user

    def create_user(self, username, email, password=None, **fields):
        fields.setdefault('is_staff', False)
        fields.setdefault('is_superuser', False)
        return self.__create_user(username, email, password, **fields)

    def create_superuser(self, username: str, email: str, password: str, **fields):
        fields.setdefault('is_staff', True)
        fields.setdefault('is_superuser', True)
        return self.__create_user(username, email, password, **fields)


class MusicianUser(AbstractBaseUser, PermissionsMixin):

    class Meta:
        verbose_name = "Пользователь"

    username = models.CharField(db_index=True, max_length=128, unique=True, verbose_name="Имя пользователя")
    email = models.EmailField(
        max_length=128,
        validators=[validators.validate_email],
        unique=True,
        blank=False,
        verbose_name="Почта"
    )
    chords = models.ManyToManyField(Chord, blank=True)
    melodies = models.ManyToManyField(Melody, blank=True)
    is_staff = models.BooleanField(default=False)
    is_active = models.BooleanField(default=True)

    USERNAME_FIELD = 'email'
    REQUIRED_FIELDS = ('username',)

    objects = MusicianUserManager()

    def __str__(self):
        return self.username
