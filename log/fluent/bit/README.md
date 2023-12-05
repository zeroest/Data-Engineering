
# [Install](https://github.com/fluent/fluent-bit-docs/tree/master/installation)

## [Install on Linux](https://github.com/fluent/fluent-bit-docs/tree/master/installation/linux)

## Source build and Install 
[[DOCS] Fluent Bit - Build and Install](https://docs.fluentbit.io/manual/installation/sources/build-and-install)

### Requirements
- CMake >= 3.12
- Flex
- Bison >= 3
- YAML headers
- OpenSSL headers

### Build and Install

[CentOS Reference](https://github.com/fluent/fluent-bit/blob/master/dockerfiles/Dockerfile.centos7)

[Debian Reference](https://github.com/fluent/fluent-bit/blob/master/dockerfiles/Dockerfile)

```bash
sudo yum install -y \
  git gcc make cmake flex bison g++ \
  libyaml \ # debian: libyaml-dev
  openssl   # debian: libssl-dev

# Develop version
git clone https://github.com/fluent/fluent-bit
# Check current latest version on https://github.com/fluent/fluent-bit/releases
wget https://github.com/fluent/fluent-bit/archive/refs/tags/v2.2.0.tar.gz

tar xzvf v2.2.0.tar.gz
cd fluent-bit-2.2.0/build/

cmake ../

make

make install
```
